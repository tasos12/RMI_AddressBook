package server;

import java.sql.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddressBookServer {

	private static final String HOST = "localhost";
	private static final int PORT_DB = 3306;
	private static final String TABLE = "addressbook";
	private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT_DB + "/" + TABLE;
	private static final String USERNAME = "tasos";
	private static final String PASSWORD = "password";
	private static final int PORT_RMI = Registry.REGISTRY_PORT;
	private static final String OBJ_NAME = "common.common.AddressBook";

	
	public static void main(String[] args) throws Exception
	{
		try {
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = conn.createStatement();
			AddressBookImpl robj = new AddressBookImpl(st);
			Registry registry = LocateRegistry.createRegistry(PORT_RMI);
			System.out.println("Registry at: " + HOST + "\\" + PORT_RMI);
			registry.rebind(OBJ_NAME, robj);
			System.out.println("Object " + OBJ_NAME + " Bound");
		} catch (Exception e) {
			System.err.println("common.common.AddressBook exception:");
	        e.printStackTrace();
		}
		
	}

}
