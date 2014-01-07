package servlet.contactgroup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dao.IDAOContact;
import dao.IDAOContactGroup;

import util.ApplicationContextUtils;
import util.StringUtil;

/**
 * Servlet implementation class CreateContactGroupServlet
 */
public class CreateContactGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/Views/ContactGroup/CreateContactGroup.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateContactGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		String idContact = StringUtil.getValue(request.getParameter("idContact"));
		List contactGroups = dao.getContactGroupByIdContact(idContact);
		request.setAttribute("contactGroups", contactGroups);
		request.setAttribute("title", "Create Contact Group");
		System.out.println("================================================================ doGet CreateContactGroupServlet idContact "+ idContact +" ===============================");
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String groupContactName = StringUtil.getValue(request.getParameter("groupContactName"));
		String idContact = StringUtil.getValue(request.getParameter("idContact"));
		
		//DAOContactGroup dao = new DAOContactGroup();
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContactGroup dao = (IDAOContactGroup)context.getBean("DAOContactGroup");
		
		PrintWriter out = response.getWriter();
		request.setAttribute("title", "Create Contact Group");
		String VueWithIdContact = "/Views/ContactGroup/CreateContactGroup.jsp?idContact="+idContact;
		if(dao.createContactGroup(groupContactName, idContact)){
			request.setAttribute("titleResult", "Contact group succesfully added");
			request.setAttribute("result", "Contact group has been added");
		} else {
			request.setAttribute("titleErrors", "Failed to Add Contact group");
			request.setAttribute("errors", "Failure when adding contact group : " + groupContactName);
		}
		
		IDAOContact daoContact = (IDAOContact)context.getBean("DAOContact");
		System.out.println("================================================================ doGet CreateContactGroupServlet idContact "+ idContact +" ===============================");
		List contactGroups = daoContact.getContactGroupByIdContact(idContact);
		request.setAttribute("contactGroups", contactGroups);
		
		this.getServletContext().getRequestDispatcher(VueWithIdContact).forward(request, response);
	}

}
