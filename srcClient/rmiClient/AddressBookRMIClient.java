package rmiClient;

import common.AddressBookClient;
import common.AddressBook;
import common.BookEntry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddressBookRMIClient extends AddressBookClient{

    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;
    private static final String OBJ_NAME = "common.common.AddressBook";

    public static void main(String[] args) {
        try	{
            AddressBookRMIClient client = new AddressBookRMIClient();
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            AddressBook addressBook = (AddressBook)registry.lookup(OBJ_NAME);

            client.menuSelect(addressBook);

        } catch (RemoteException re) {
            System.out.println("Remote Exception");
            re.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }
    }

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
