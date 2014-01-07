package domain.impl;

import domain.IContact;
import domain.IPhoneNumber;

public class PhoneNumber implements IPhoneNumber {
	private long id;
	private int version;
	private String phoneKind;
	private String phoneNumber;
	private IContact contact;
	
	public PhoneNumber() {}
	
	public PhoneNumber(long id, String phoneKind, String phoneNumber, Contact contact) {
		this.id = id;
		this.phoneKind = phoneKind;
		this.phoneNumber = phoneNumber;
		this.contact = contact;
	}
	
	public PhoneNumber(String phoneKind, String phoneNumber, Contact contact) {
		this.phoneKind = phoneKind;
		this.phoneNumber = phoneNumber;
		this.contact = contact;
	}

	public PhoneNumber(String phoneKind, String phoneNumber) {
		this.phoneKind = phoneKind;
		this.phoneNumber = phoneNumber;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneKind() {
		return phoneKind;
	}

	public void setPhoneKind(String phoneKind) {
		this.phoneKind = phoneKind;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public IContact getContact(){
		return contact;
	}
	public void setContact(IContact contact){
		this.contact = contact;
	}
}
