package dao;

import java.util.List;
import java.util.Set;

import domain.IAddress;
import domain.IContact;
import domain.IPhoneNumber;

public interface IDAOContact {
	public boolean createContact(String fname, String lname, String email, IAddress address, Set<IPhoneNumber> profiles, int numSiret);

	public boolean updateContact(IContact c, String fname, String lname, String email, 
			String street, String zip, String city, String country, String home, String office, String mobile, int siretnum);

	public boolean deleteContact(String id);

	public List searchContact(final String fname, final String lname, final String email, final IAddress address,
			final String home, final String office, final String mobile);

	public Object[] getContactById(final String id);

	public List getAllContacts();
	
	public List getContactGroupByIdContact(String idContact);
	
	public boolean generateContacts();
}
