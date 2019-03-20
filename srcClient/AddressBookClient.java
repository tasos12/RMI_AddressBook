import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AddressBookClient {

	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT;
	private static final String OBJ_NAME = "AddressBook";

	private static Scanner input;
	
	public static void main(String[] args)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			AddressBook addressBook = (AddressBook) registry.lookup(OBJ_NAME);

			String result;
			input = new Scanner(System.in);
			int choice = 0;
			System.out.println("Select option: (1-6)");
			while(choice != 6){
				System.out.println("1.Insert\n2.Update\n3.Select\n4.Delete\n5.SelectAll\n6.Exit");
				choice = input.nextInt();
				result = executeOption(choice, addressBook);
				System.out.println(result);
			}
			input.close();
		}
		catch (RemoteException re)
		{
			System.out.println("Remote Exception");
			re.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}

	private static String executeOption(int ch, AddressBook ab) throws RemoteException{
		BookEntry tempEntry;
		int tempId;
		String result ="No case selected";
		switch(ch){
			case 1:	tempEntry = getEntryfromInput();
					result = ab.insert(tempEntry);
					break;
			case 2: System.out.print("Select id to update: ");
					tempId = input.nextInt();
					tempEntry = getEntryfromInput();
					result = ab.update(tempId, tempEntry);
					break;
			case 3: System.out.print("Select id to print: ");
					tempId = input.nextInt();
					result = ab.select(tempId);
					break;
			case 4: System.out.print("Select id to delete: ");
					tempId = input.nextInt();
					result = ab.delete(tempId);
					break;
			case 5: result = ab.selectAll();
					break;
		}

		return result;
	}

	private static BookEntry getEntryfromInput(){
		System.out.print("New Id = ");
		int tempId = input.nextInt();
		input.nextLine();
		System.out.print("Fullname = ");
		String tempFullname = input.nextLine();
		System.out.print("Email = ");
		String tempEmail = input.nextLine();
		System.out.print("Phone = ");
		int tempPhone = input.nextInt();
		return new BookEntry(tempId, tempFullname, tempEmail, tempPhone);
	}

}
