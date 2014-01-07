package controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

import util.ApplicationContextUtils;
import dao.IDAOContact;
import domain.IAddress;
import domain.IContact;
import domain.IEntreprise;
import domain.IPhoneNumber;
import domain.impl.Address;
import domain.impl.Contact;
import domain.impl.PhoneNumber;

public class ContactController {

	private String numSiret;
	private IContact contact;
	private String HomePhone;
	private String mobilePhone;
	private String officePhone;
	private String action;
	private List<IContact> resultSearch;
	private int versionContact;

	public ContactController(){
	}

	public void init(ComponentSystemEvent event){		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		numSiret = "";
		if(params.get("action") != null){
			action = params.get("action");
		}

		if(action.equals("create") || action.equals("search")){
			contact = new Contact();
			contact.setAddress(new Address());
			HomePhone = "";mobilePhone="";officePhone="";
		}
		else if(action.equals("update")){
			String idContact = params.get("idContact");
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
			if((idContact == null) || (idContact.isEmpty())){
				return;
			}
			IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
			Object[] result = dao.getContactById(idContact);
			contact = (Contact)result[0];
			versionContact = contact.getVersion();
			if(contact instanceof IEntreprise){
				numSiret = ((IEntreprise)contact).getNumSiret() + "";
			}
			if (contact.getProfiles() != null) {
				for (IPhoneNumber phoneNumber : contact.getProfiles()) {
					if (phoneNumber.getPhoneKind().equalsIgnoreCase("home")) {
						HomePhone = phoneNumber.getPhoneNumber();
					}
					if (phoneNumber.getPhoneKind().equalsIgnoreCase(
							"office")) {
						officePhone = phoneNumber.getPhoneNumber();
					}
					if (phoneNumber.getPhoneKind().equalsIgnoreCase(
							"mobile")) {
						mobilePhone = phoneNumber.getPhoneNumber();
					}
				}
			}
		}
	}

	public String save(){
		FacesContext contexte = FacesContext.getCurrentInstance();

		int numberSiret = -1;
		if(! numSiret.isEmpty()){
			try{
				numberSiret = Integer.parseInt(numSiret);
			} catch(NumberFormatException e) {}
		}

		String fname = contact.getFirstname();
		String lname = contact.getLastname();
		String email = contact.getEmail();

		String street = contact.getAddress().getStreet();
		String zip = contact.getAddress().getZip();
		String city = contact.getAddress().getCity();
		String country = contact.getAddress().getCountry();

		IAddress address;
		if(street.isEmpty() && zip.isEmpty() && city.isEmpty() && country.isEmpty()){
			address = null;
		} else {
			address = new Address(street, city, zip, country);
		}

		String homepn = getHomePhone();
		String officepn = getOfficePhone();
		String mobilepn = getMobilePhone();

		Set<IPhoneNumber> profiles;
		if(homepn.isEmpty() && officepn.isEmpty() && mobilepn.isEmpty()){
			profiles = null;
		} else {
			profiles = new HashSet<IPhoneNumber>();
			if(! homepn.isEmpty()){
				PhoneNumber home = new PhoneNumber("home", homepn);
				profiles.add(home);
			}
			if(! officepn.isEmpty()){
				PhoneNumber office = new PhoneNumber("office", officepn);
				profiles.add(office);
			}
			if(! mobilepn.isEmpty()){
				PhoneNumber mobile = new PhoneNumber("mobile", mobilepn);
				profiles.add(mobile);
			}
		}

		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		if(action.equals("create")){
			if(dao.createContact(fname, lname, email, address, profiles, numberSiret)){
				contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
				MessageController.getCurrentMessage(false, "Contact has been created", "Contact succesfully created");
			} else {
				contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
				MessageController.getCurrentMessage(true, "Failed to create contact", "Failure when creating contact");
			}
		}
		else if(action.equals("update")){
			if(versionContact == contact.getVersion()){
				if(dao.updateContact(contact, fname, lname, email, street, zip, city, country, homepn, officepn, mobilepn, numberSiret)){
					contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
					MessageController.getCurrentMessage(false, "Contact succesfully updated", "Contact has been updated");
				} else {
					contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
					MessageController.getCurrentMessage(true, "Failed to update contact", "Failure when updating contact");
				}
			}
		}

		return null;
	}

	public String generateContacts(){
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		FacesContext contexte = FacesContext.getCurrentInstance();
		if(dao.generateContacts()){
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
			MessageController.getCurrentMessage(false, "Generate contact : Contacts succesfully added", "Contact has been added");			
		}
		else{
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
			MessageController.getCurrentMessage(false, "Generate contact", "Failed to generate contacts");	
		}
		

		return null;
	}

	public String search(){
		FacesContext contexte = FacesContext.getCurrentInstance();
		String fname = contact.getFirstname();
		String lname = contact.getLastname();
		String email = contact.getEmail();

		String street = contact.getAddress().getStreet();
		String zip = contact.getAddress().getZip();
		String city = contact.getAddress().getCity();
		String country = contact.getAddress().getCountry();

		IAddress address = new Address(street, city, zip, country);

		String homepn = getHomePhone();
		String officepn = getOfficePhone();
		String mobilepn = getMobilePhone();

		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		resultSearch = dao.searchContact(fname, lname, email, address, homepn, officepn, mobilepn);
		System.out.println("listResultat = " + resultSearch.size());
		if(!resultSearch.isEmpty()){
			return "result";
		}
		else{
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
			MessageController.getCurrentMessage(true, "Search contact", "The search result is empty");
		}
		return null;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<IContact> getResultSearch() {
		return resultSearch;
	}

	public void setResultSearch(List<IContact> resultSearch) {
		this.resultSearch = resultSearch;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public IContact getContact() {
		return contact;
	}

	public void setContact(IContact contact) {
		this.contact = contact;
	}


	public String getHomePhone() {
		return HomePhone;
	}

	public void setHomePhone(String homePhone) {
		HomePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}


}
