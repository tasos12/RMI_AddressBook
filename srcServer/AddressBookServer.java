import java.sql.*; 
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddressBookServer {

	private static final int PORT_RMI = 2000;
	private static final String OBJ_NAME = "AddressBook";

	
	public static void main(String[] args) throws Exception
	{
		try {
			AddressBookImpl robj = new AddressBookImpl();
			Registry registry = LocateRegistry.createRegistry(PORT_RMI);
			registry.rebind(OBJ_NAME, robj);
			System.out.println("Object " + OBJ_NAME + " Bound");
		} catch (Exception e) {
	        System.err.println("AddressBook exception:");
	        e.printStackTrace();
		}
		
	}

}
