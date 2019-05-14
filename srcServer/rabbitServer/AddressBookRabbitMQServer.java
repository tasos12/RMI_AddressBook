package rabbitServer;


import com.rabbitmq.client.*;
import common.AddressBookImpl;
import common.BookEntry;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

import static common.Constants.*;

public class AddressBookRabbitMQServer {

    private static final String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        //sql setup
        java.sql.Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement st = conn.createStatement();
        AddressBookImpl addressBook = new AddressBookImpl(st);

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.basicQos(1);
            System.out.println(" [x] Awaiting RPC requests");
            Object monitor = new Object();

            //Insert callback function
            DeliverCallback addressBookCallback = (consumerTag, delivery) -> {

                //Setup properties
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    //From byte[] to BookEntry
                    BookEntry entry = new BookEntry();
                    int id;
                    Map<String, Object> headers = delivery.getProperties().getHeaders();
                    Object methodName1 = headers.get(METHOD_KEY);
                    String methodName = methodName1.toString();

                    switch (methodName){
                        case INSERT:
                            entry.fromBytes(delivery.getBody());
                            response = addressBook.insert(entry);
                            break;
                        case UPDATE:
                            entry.fromBytes(delivery.getBody());
                            id = (int) headers.get(ID_KEY);
                            response = addressBook.update(id, entry);
                            break;
                        case SELECT:
                            id = (int) headers.get(ID_KEY);
                            response = addressBook.select(id);
                            break;
                        case DELETE:
                            id = (int) headers.get(ID_KEY);
                            response = addressBook.delete(id);
                            break;
                        case SELECTALL:
                            response = addressBook.selectAll();
                            break;
                    }
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    //Publish response for common
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                    // RabbitMq consumer worker thread notifies the RPC rmiServer owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(ADDRESSBOOK_QUEUE, false, addressBookCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC common.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
