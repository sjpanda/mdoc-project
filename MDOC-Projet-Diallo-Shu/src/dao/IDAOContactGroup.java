package dao;

import java.util.List;

import domain.IContactGroup;

public interface IDAOContactGroup {
	public boolean createContactGroup(String name , String idContact);

	public List getAllContactGroup();

	public IContactGroup getContactGroupById(String id);

	public boolean addContact(String[] contacts, String idContactGroup);

	public List getContactGroupByIdContactGroup(final String idContactGroup);
	
	public boolean deleteContactGroup(IContactGroup contactGroup);
}
