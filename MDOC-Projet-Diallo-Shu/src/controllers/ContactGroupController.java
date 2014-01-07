package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.context.ApplicationContext;

import util.ApplicationContextUtils;
import dao.IDAOContact;
import dao.IDAOContactGroup;
import domain.IContact;
import domain.IContactGroup;
import domain.impl.Contact;
import domain.impl.ContactGroup;

public class ContactGroupController {

	private IContactGroup contactGroup;
	private IContact contact;
	private List<IContactGroup> contactGroups;
	private List<IContact> contacts;
	private Map<Long, Boolean> contactAdded;
	private String action;
	private String idContactGroup;

	public void init(ComponentSystemEvent event){		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();

		String idContact = params.get("idContact");
		String idContactGroup = params.get("idContactGroup");
		if(params.get("action") != null){
			action = params.get("action");
		}
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		if(idContact != null){
			System.out.println("idContact = " + idContact);

			Object[] result = dao.getContactById(idContact);
			contact = (Contact)result[0];
			contactGroups = new ArrayList<IContactGroup>(contact.getBooks());

		}
		if(idContactGroup != null){
			contacts = new ArrayList<IContact>();
			this.idContactGroup= idContactGroup;

			if(action.equals("create")){
				contactAdded = new HashMap<Long, Boolean>();
				contacts = getAllContactForAdd(idContactGroup);
				//a changer
				for(IContact c : contacts){
					contactAdded.put(c.getId(), false);
				}
			}
			else{
				IDAOContactGroup daoContactGroup  = (IDAOContactGroup)context.getBean("DAOContactGroup");
				//IDAOContactGroup daoContactGroup = new DAOContactGroup();
				contacts = daoContactGroup.getContactGroupByIdContactGroup(idContactGroup);
			}

		}
		contactGroup = new ContactGroup();

	}

	public String save(){
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContactGroup dao  = (IDAOContactGroup)context.getBean("DAOContactGroup");
		FacesContext contexte = FacesContext.getCurrentInstance();
		
		//IDAOContactGroup dao = new DAOContactGroup();
		String groupContactName = contactGroup.getGroupName();
		String idContact = contact.getId()+"";
		
		if(dao.createContactGroup(groupContactName, idContact)){
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
			MessageController.getCurrentMessage(false, "Contact group succesfully added", "Contact group has been added");			
		}
		else{
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
			MessageController.getCurrentMessage(false, "Failed to Add Contact group", "Failure when adding contact group");	
		}
		
		IDAOContact daoContact = (IDAOContact)context.getBean("DAOContact");
		//IDAOContact daoContact = new DAOContact();
		Object[] result = daoContact.getContactById(idContact);
		contact = (Contact)result[0];
		contactGroups = new ArrayList<IContactGroup>(contact.getBooks());
		return null;
	}

	public String cancel(){
		contactGroup.setGroupName("");
		return null;

	}

	public String addContact(){
		ArrayList<String> listContact1 = new ArrayList<String>();
		FacesContext contexte = FacesContext.getCurrentInstance();
		for(IContact c : contacts){
			if(contactAdded.get(c.getId())){
				listContact1.add(c.getId()+"");
			}
		}
		String[] list = new String[listContact1.size()];
		for(int i = 0 ; i < listContact1.size() ; i++){
			list[i]= listContact1.get(i);
		}
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContactGroup dao  = (IDAOContactGroup)context.getBean("DAOContactGroup");
		//IDAOContactGroup dao = new DAOContactGroup();
		if(dao.addContact(list, idContactGroup)){
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", null));
			MessageController.getCurrentMessage(false, "Contacts succesfully added", "Contacts have been added");			
		}
		else{
			contexte.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect", null));
			MessageController.getCurrentMessage(false, "Failed to Add Contacts", "Failure when adding contacts");	
		}

		return null;
	}

	public IContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(IContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}

	public IContact getContact() {
		return contact;
	}

	public void setContact(IContact contact) {
		this.contact = contact;
	}

	public List<IContactGroup> getContactGroups() {
		return contactGroups;
	}

	public void setContactGroups(List<IContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}

	public List<IContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<IContact> contacts) {
		this.contacts = contacts;
	}

	public Map<Long, Boolean> getContactAdded() {
		return contactAdded;
	}

	public void setContactAdded(Map<Long, Boolean> contactAdded) {
		this.contactAdded = contactAdded;
	}

	public String getIdContactGroup() {
		return idContactGroup;
	}

	public void setIdContactGroup(String idContactGroup) {
		this.idContactGroup = idContactGroup;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private List getAllContactForAdd(String idContactGroup){
		List contactsUnique = new ArrayList();
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(FacesContext.getCurrentInstance());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		IDAOContactGroup daoContactGroup = (IDAOContactGroup)context.getBean("DAOContactGroup");
		
		List contacts = dao.getAllContacts();
		IContactGroup contactGroup = daoContactGroup.getContactGroupById(idContactGroup);
		Set<IContact> contactsGroup = contactGroup.getContacts();
		
		boolean existe = false;
		for(int i = 0 ; i < contacts.size() ; i++){
			existe = false;
			for(IContact c : contactsGroup){
				if(c.getId() == ((IContact)contacts.get(i)).getId()){
					existe = true;
					break;
				}
			}
			if(!existe){
				contactsUnique.add(contacts.get(i));
			}
		}

		return contactsUnique;
	}

}
