import java.io.Serializable;

public class BookEntry implements Serializable{
	
	private static final long serialVersionUID = 3738722673388137167L;
	private int id;
	private String fullname;
	private String email;
	private int phoneNumber;
	
	public BookEntry(int id, String fullname, String email, int phoneNumber) {
		this.id = id;
		this.setFullname(fullname);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
	}
	
	public BookEntry(String fullname, String email, int phoneNumber) {
		this.setFullname(fullname);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString(){
		String text = id + ". " + fullname + " " + email + " " + phoneNumber + "\n";
		return text;
	}

}
