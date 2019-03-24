import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;


/**
 * The Address Book implementation implements the AddressBook class and provides the server with 
 * its basic functionalities
 * 
 * @author Gkagkas Anastasios
 * @since 2019-03
 */
public class AddressBookImpl extends UnicastRemoteObject implements AddressBook{

	private static final long serialVersionUID = 8197529583444611357L;
	
	private Statement statement;
	private ResultSet result;
	
	public AddressBookImpl(Statement s) throws RemoteException{
		statement = s;
	}
	

	/**
	 * Inserts the address book entry specified by the client to the database
	 * @param entry the BookEntry to be inserted.
	 * @return  	the number of lines changed in database
	 * @see			BookEntry
	 */
	public String insert(BookEntry entry) throws RemoteException{
		String text = "Error while inserting";
		String query = "insert into users values (" + entry.getId() + ", \"" + entry.getFullname()
						 + "\", \"" + entry.getEmail() + "\", " + entry.getPhoneNumber() + ")";
		System.out.println(query);
		try{
			int r = statement.executeUpdate(query);
			text = updateResult2String(r);
		} catch(Exception e){
			System.out.println(e);
		}
		return text;
	}


	/**
	 * Updates the BookEntry specified by the id with the new BookEntry
	 * @param id 	the id of BookEntry to update
	 * @param entry the BookEntry to replace the old one
	 * @return 		the number of lines updated
	 * @see			BookEntry
	 */
	public String update(int id, BookEntry entry) throws RemoteException{
		String text = "Error while updating";
		String query = "update users set id = " + entry.getId() + ", fullname = \"" + entry.getFullname()
						+ "\", email = \"" + entry.getEmail() + "\", phone = " + entry.getPhoneNumber()
						+ " where id = " + id;
		System.out.println(query);
		try{
			int r = statement.executeUpdate(query);
			text = updateResult2String(r);
		} catch(Exception e){
			System.out.println(e);
		}
		return text;
	}

	/**
	 * Selects the BookEntry to return to the client
	 * @param id	the id of BookEntry to be returned
	 * @return		the selected Bookentry as string
	 */
	public String select(int id) throws RemoteException{
		String text = "Error while selecting";
		String query = "select * from users where id = " + id;
		System.out.println(query);
		try{
			result = statement.executeQuery(query);
			text = result2String(result);
		} catch(Exception e){
			System.out.println(e);
		}
		return text;
	}

	/**
	 * Deletes the selected BookEntry from database
	 * @param id	the id of
	 * @return 		a text with the number of lines deleted 
	 */
	public String delete(int id) throws RemoteException{
		String text = "Error while deleting";
		String query = "delete from users where id = " + id;
		System.out.println(query);
		try{
			int r = statement.executeUpdate(query);
			text = updateResult2String(r);
		} catch(Exception e){
			System.out.println(e);
		}
		return text;
	}

	/**
	 * Selects and returns all database Book Entries as text
	 * @return		a text with all the registered BookEntries
	 */
	public String selectAll() throws RemoteException{
		String text = "";
		String query = "select * from users";
		System.out.println(query);
		try{
			result = statement.executeQuery(query);
			text = result2String(result);
		} catch(Exception e) {
			System.out.println(e);
		}
		return text;
	}

	/**
	 * Transforms the result of the query to string
	 * @param result	the ResultSet of the query result
	 * @return			a text from the ResultSet
	 */
	private String result2String(ResultSet result) throws Exception{
		String text = "";
		while(result.next()){
			int tempId = result.getInt("id");
			String tempFn = result.getString("fullname");
			String tempMail = result.getString("email");
			int tempPhone = result.getInt("phone");
			BookEntry temp = new BookEntry(tempId, tempFn, tempMail, tempPhone);
			text += temp.toString();
		}
		return text;
	}
	
	/**
	 * Transforms the integer result of the query to string
	 * @param result	an integer as a result of query
	 * @return			a text based on the result
	 */
	private String updateResult2String(int result) throws Exception{
		String text = "Number of lines updated: " + result;
		return text;
	}

}
