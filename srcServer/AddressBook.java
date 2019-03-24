
import java.rmi.*;

public interface AddressBook extends Remote{
	
	/**
	 * Inserts the address book entry specified by the client to the database
	 * @param entry the BookEntry to be inserted.
	 * @return  	the number of lines changed in database
	 * @see			BookEntry
	 */
	String insert(BookEntry entry) throws RemoteException;

	/**
	 * Updates the BookEntry specified by the id with the new BookEntry
	 * @param id 	the id of BookEntry to update
	 * @param entry the BookEntry to replace the old one
	 * @return 		the number of lines updated
	 * @see			BookEntry
	 */
	String update(int id, BookEntry entry) throws RemoteException;

	/**
	 * Selects the BookEntry to return to the client
	 * @param id	the id of BookEntry to be returned
	 * @return		the selected Bookentry as string
	 */
	String select(int id) throws RemoteException;

	/**
	 * Deletes the selected BookEntry from database
	 * @param id	the id of
	 * @return 		a text with the number of lines deleted 
	 */
	String delete(int id) throws RemoteException;

	/**
	 * Selects and returns all database Book Entries as text
	 * @return		a text with all the registered BookEntries
	 */
	String selectAll() throws RemoteException;
}
