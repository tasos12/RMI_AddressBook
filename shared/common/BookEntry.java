package common;

import java.io.*;

public class BookEntry implements Serializable{

	private static final long serialVersionUID = 3738722673388137167L;
	private int id;
	private String fullname;
	private String email;
	private int phoneNumber;

	public BookEntry(){}

	public BookEntry(int id, String fullname, String email, int phoneNumber) {
		this.id = id;
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

	public byte[] toBytes() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;

		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			return bos.toByteArray();
		} catch (IOException ex) {
		} finally {
			try {
				bos.close();
			} catch (IOException e) {}
		}
		return new byte[0];
	}

	public void fromBytes(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			BookEntry o = (BookEntry) in.readObject();
			this.setEmail(o.getEmail());
			this.setFullname(o.getFullname());
			this.setId(o.getId());
			this.setPhoneNumber(o.getPhoneNumber());
		} catch (ClassNotFoundException | IOException e) {
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {}
		}
	}

}
