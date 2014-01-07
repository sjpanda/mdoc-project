package domain.impl;

import java.util.HashSet;
import java.util.Set;

import domain.IAddress;
import domain.IContact;
import domain.IContactGroup;
import domain.IPhoneNumber;

public class Contact implements IContact {
	private long id;
	private int version;
	private String firstname;
	private String lastname;
	private String email;
	private IAddress address;
	private Set<IPhoneNumber> profiles;
	private Set<IContactGroup> books;
	
	public int getVersion(){
		return version;
	}
	
	public Contact(long id, String firstname, String lastname, String email, Address address, Set<IPhoneNumber> profiles, Set<IContactGroup> books){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.profiles = profiles;
		this.books = books;
	}

	public Contact(long id, String firstname, String lastname, String email, Address address){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.profiles = new HashSet<IPhoneNumber>();
		this.books = new HashSet<IContactGroup>();
	}
	
	public Contact() {
		profiles = new HashSet<IPhoneNumber>();
		books = new HashSet<IContactGroup>();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public IAddress getAddress() {
		return address;
	}
	public void setAddress(IAddress address) {
		this.address = address;
	}
	public Set<IPhoneNumber> getProfiles() {
		return profiles;
	}
	public void setProfiles(Set<IPhoneNumber> profiles) {
		this.profiles = profiles;
	}
	
	public Set<IContactGroup> getBooks() {
		return books;
	}

	public void setBooks(Set<IContactGroup> books) {
		this.books = books;
	}
}
