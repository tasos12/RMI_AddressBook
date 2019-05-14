package webServiceClient;

import common.AddressBook;
import common.AddressBookClient;
import common.BookEntry;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class AddressBookWebServiceClient extends AddressBookClient {

    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("http://localhost:8881/addressBook");
        } catch (MalformedURLException e) {}

        QName qname = new QName("http://common/", "AddressBookImplService");
        Service service = Service.create(url, qname);
        AddressBook addressBook = service.getPort(AddressBook.class);

        AddressBookWebServiceClient abwsClient = new AddressBookWebServiceClient();
        abwsClient.menuSelect(addressBook);
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
                    result = ab.insert(tempEntry);
                    break;
                case 2:
                    System.out.print("Select id to update: ");
                    tempId = input.nextInt();
                    tempEntry = getEntryFromInput();
                    result = ab.update(tempId, tempEntry);
                    break;
                case 3:
                    System.out.print("Select id to print: ");
                    tempId = input.nextInt();
                    result = ab.select(tempId);
                    break;
                case 4:
                    System.out.print("Select id to delete: ");
                    tempId = input.nextInt();
                    result = ab.delete(tempId);
                    break;
                case 5:
                    result = ab.selectAll();
                    break;
            }
        } catch (RemoteException e){}

        return result;
    }
}
