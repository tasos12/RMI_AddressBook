import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Set;

public class AddressBookImpl extends UnicastRemoteObject implements AddressBook{

	private static final long serialVersionUID = 8197529583444611357L;
	
	private Hashtable<Integer, BookEntry> database;
	private int id = 0;
	
	public AddressBookImpl() throws RemoteException{
		database = new Hashtable<Integer, BookEntry>();
	}
	
	public synchronized String insert(BookEntry entry) throws RemoteException{
		id++;
		entry.setId(id);
		database.put(id, entry);
		return "User Inserted";
	}

	public synchronized String update(int id, BookEntry entry) throws RemoteException{
		if(!database.containsKey(id)) {
			return "User was not found";
		}
		database.get(id);
		return "User updated";
	}

	public synchronized String select(int id) throws RemoteException{
		if(!database.containsKey(id)) {
			return "Id not found";
		}
		
		return database.get(id).toString();
	}

	public synchronized String delete(int id) throws RemoteException{
		if(!database.containsKey(id)) {
			return "Id not found";
		}
		database.remove(id);
		return "User deleted";
	}

	public synchronized String selectAll() throws RemoteException{
		String text = "";
		Set<Integer> keys = database.keySet();
		for(int key: keys) {
			text += database.get(key).toString();
		}
		return text;
	}

}
