package servlet.contactgroup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dao.IDAOContact;
import dao.IDAOContactGroup;
import domain.IContact;
import domain.IContactGroup;

import util.ApplicationContextUtils;
import util.StringUtil;

/**
 * Servlet implementation class AddContactToContactGroup
 */
public class AddContactToContactGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddContactToContactGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idContactGroup = StringUtil.getValue(request.getParameter("idContactGroup"));
		List contacts = this.getAllContactForAdd(idContactGroup);
		request.setAttribute("contacts", contacts);
		request.setAttribute("title", "Add Contact");
		RequestDispatcher rd = request.getRequestDispatcher("Views/ContactGroup/AddContactToContactGroup.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] contacts = request.getParameterValues("contacts");
		String idContactGroup = StringUtil.getValue(request.getParameter("idContactGroup"));

		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContactGroup dao = (IDAOContactGroup)context.getBean("DAOContactGroup");

		PrintWriter out = response.getWriter();
		if(dao.addContact(contacts, idContactGroup)){
			request.setAttribute("titleResult", "Contacts succesfully added");
			request.setAttribute("result", "Contacts have been added");
		} else {
			request.setAttribute("titleErrors", "Failed to Add Contacts");
			request.setAttribute("errors", "Failure when adding contacts");
		}

		List contactsToAdd = this.getAllContactForAdd(idContactGroup);
		request.setAttribute("contacts", contactsToAdd);
		request.setAttribute("title", "Add Contact");
		RequestDispatcher rd = request.getRequestDispatcher("Views/ContactGroup/AddContactToContactGroup.jsp");
		rd.forward(request, response);

	}

	private List getAllContactForAdd(String idContactGroup){
		List contactsUnique = new ArrayList();
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
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
