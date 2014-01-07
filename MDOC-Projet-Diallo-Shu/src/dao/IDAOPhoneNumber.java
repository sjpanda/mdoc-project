package dao;

import java.util.List;

public interface IDAOPhoneNumber {
	public List getPhoneNumbersByIdContact(long idContact);
	
	public boolean deletePhoneNumber(long id);
}
