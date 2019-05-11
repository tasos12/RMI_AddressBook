package rmiServer;

import common.AddressBookImpl;

import java.sql.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static common.Constants.*;

public class AddressBookRMIServer {

	
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
