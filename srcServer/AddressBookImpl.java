import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class AddressBookImpl extends UnicastRemoteObject implements AddressBook{

	private static final long serialVersionUID = 8197529583444611357L;
	
	private Statement statement;
	private ResultSet result;
	
	public AddressBookImpl(Statement s) throws RemoteException{
		statement = s;
	}
	
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

	private String result2String(ResultSet r) throws Exception{
		String text = "";
		while(r.next()){
			int tempId = r.getInt("id");
			String tempFn = r.getString("fullname");
			String tempMail = r.getString("email");
			int tempPhone = r.getInt("phone");
			BookEntry temp = new BookEntry(tempId, tempFn, tempMail, tempPhone);
			text += temp.toString();
		}
		return text;
	}
	
	private String updateResult2String(int result) throws Exception{
		String text = "Number of lines updated: " + result;
		return text;
	}

}
