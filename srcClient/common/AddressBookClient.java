package common;

import java.io.IOException;
import java.rmi.*;
import java.util.Scanner;

public abstract class AddressBookClient {

	public Scanner input;

	public AddressBookClient(){
		input = new Scanner(System.in);
	}

	public void menuSelect(AddressBook addressBook) {
		String result;

		int choice = 0;
		System.out.println("Select option: (1-6)");
		while(choice != 6){
			System.out.println("1.Insert\n2.Update\n3.Select\n4.Delete\n5.SelectAll\n6.Exit");
			choice = input.nextInt();
			result = executeOption(choice,  addressBook);
			System.out.println(result);
		}
		input.close();
	}

	public abstract String executeOption(int ch, AddressBook ab);

	public BookEntry getEntryFromInput(){
		String tempEmail, tempId, tempPhone;

		//Get ID
		do {
			System.out.print("New ID = ");
			tempId = input.next();
		} while (!isInteger(tempId));

		//Get name
		input.nextLine();
		System.out.print("Fullname = ");
		String tempFullname = input.nextLine();

		//Get email
		do {
			System.out.print("Email = ");
			tempEmail = input.nextLine();
		} while (!isEmailLegit(tempEmail));

		//Get phone
		do {
				System.out.print("Phone = ");
				tempPhone = input.nextLine();
		} while(!isInteger(tempPhone));

		int id = Integer.parseInt(tempId);
		int phone = Integer.parseInt(tempPhone);
		return new BookEntry(id, tempFullname, tempEmail, phone);
	}

	private boolean isEmailLegit(String input) {
		if(input.contains("@")) {
			String[] splitInput1 = input.split("@");
			if(splitInput1[1].contains(".")) {
				return true;
			}
		}
		System.out.println("Email  must be formated as \"xxxx@xxx.xxx\"");
		return false;
	}

	private boolean isInteger(String str) {
		try {
			int d = Integer.parseInt(str);
		}
		catch(NumberFormatException nfe) {
			System.out.println("Please give a number as ID");
			return false;
		}
		return true;
	}
}
