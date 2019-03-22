
import java.rmi.*;

public interface AddressBook extends Remote{
	
	String insert(BookEntry entry) throws RemoteException;
	String update(int id, BookEntry entry) throws RemoteException;
	String select(int id) throws RemoteException;
	String delete(int id) throws RemoteException;
	String selectAll() throws RemoteException;
}
