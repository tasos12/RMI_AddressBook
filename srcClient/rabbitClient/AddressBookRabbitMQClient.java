package rabbitClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import common.AddressBook;
import common.AddressBookClient;
import common.BookEntry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import static common.Constants.*;

public class AddressBookRabbitMQClient extends AddressBookClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private Scanner input;

    public AddressBookRabbitMQClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
        input = new Scanner(System.in);
    }

    public static void main(String[] argv) {
        try (AddressBookRabbitMQClient client = new AddressBookRabbitMQClient()){
            client.menuSelect(null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String executeOption(int ch, AddressBook ab) {
        BookEntry tempEntry;
        int tempId;
        String result ="No case selected";
        try {
            switch (ch) {
                case 1:
                    tempEntry = getEntryFromInput();
                    result = call(INSERT, -1, tempEntry);
                    break;
                case 2:
                    System.out.print("Select id to update: ");
                    tempId = input.nextInt();
                    tempEntry = getEntryFromInput();
                    result = call(UPDATE, tempId, tempEntry);
                    break;
                case 3:
                    System.out.print("Select id to print: ");
                    tempId = input.nextInt();
                    result = call(SELECT, tempId, null);;
                    break;
                case 4:
                    System.out.print("Select id to delete: ");
                    tempId = input.nextInt();
                    result = call(DELETE, tempId, null);
                    break;
                case 5:
                    result = call(SELECTALL, -1, null);
                    break;
            }
        } catch (InterruptedException | IOException e) {}

        return result;
    }

    public String call(String methodName, int id, BookEntry entry) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();

        //Declaring headers
        Map<String, Object> headers = new HashMap<>();
        headers.put(METHOD_KEY, methodName);
        headers.put(ID_KEY, id);

        //Building properties
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .headers(headers)
                .contentType("application/json")
                .build();

        //Publishing message with its properties
        if(entry != null)
            channel.basicPublish("", ADDRESSBOOK_QUEUE, props, entry.toBytes());
        else
            channel.basicPublish("", ADDRESSBOOK_QUEUE, props, null);

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
