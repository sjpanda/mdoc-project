package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.context.ApplicationContext;

import util.ApplicationContextUtils;
import dao.IDAOContact;
import domain.IContact;

public class ListContactController {

	private List<IContact> contacts;
	private IContact contact;
	private String action;

	public void init(ComponentSystemEvent event){		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();

		if(params.get("action") != null){
			action = params.get("action");
		}
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		
		contacts = new ArrayList<IContact>();
		contacts = dao.getAllContacts();
//		if(contacts.size() == 0){
//			initalizeContacts();
//		}	
	}
	
//	private void initalizeContacts(){
//		int numSiret = -1;
//		ApplicationContext context = ApplicationContextUtils.getApplicationContext();
//		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
//		for(int i = 1 ; i < 10 ; i++){
//			String fname = "fname"+i;
//			String lname = "lname"+i;
//			String email = "email"+i;
//
//			String street = "street"+i;
//			String zip = "zip"+i;
//			String city = "city"+i;
//			String country = "country"+i;
//
//			IAddress address;
//			address = new Address(street, city, zip, country);
//
//
//			String homepn = "homepn"+i;;
//			String officepn = "officepn"+i;;
//			String mobilepn = "mobilepn"+i;;
//
//			Set<IPhoneNumber> profiles;
//			profiles = new HashSet<IPhoneNumber>();
//			PhoneNumber home = new PhoneNumber("home", homepn);
//			profiles.add(home);
//			PhoneNumber office = new PhoneNumber("office", officepn);
//			profiles.add(office);
//			PhoneNumber mobile = new PhoneNumber("mobile", mobilepn);
//			profiles.add(mobile);
//			
//			IContact c = new Contact();
//			c.setId(i);
//			c.setFirstname(fname);
//			c.setLastname(lname);
//			c.setEmail(email);
//			c.setAddress(address);
//			c.setProfiles(profiles);
//			
//			dao.createContact(fname, lname, email, address, profiles, numSiret);
//			contacts.add(c);
//		}
//	}

	public String delete(){
		FacesContext contexte = FacesContext.getCurrentInstance();
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		String id = contact.getId() + "";
		if(dao.deleteContact(id)){
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
		} else {
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
		}
		return null;
	}

	public List<IContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<IContact> contacts) {
		this.contacts = contacts;
	}
	
	
	public IContact getContact() {
		return contact;
	}

	public void setContact(IContact contact) {
		this.contact = contact;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


}
