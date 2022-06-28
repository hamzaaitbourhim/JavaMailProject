package model.beans;

import java.util.ArrayList;

public class MailingList {
	
	private String name;
	private ArrayList<String> emails;
	
	//constructor
	public MailingList(String name) {
		this.name = name;
		this.emails = new ArrayList<>();
	}
	
	public void add(String email) {
		this.emails.add(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getEmails() {
		return emails;
	}

	@Override
	public String toString() {
		return "MailingList [name=" + name + ", emails=" + emails + "]";
	}
	
	
	
	
	
}
