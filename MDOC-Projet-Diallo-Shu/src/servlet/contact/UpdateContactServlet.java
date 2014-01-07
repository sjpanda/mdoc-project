package servlet.contact;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.ApplicationContextUtils;
import util.StringUtil;
import dao.IDAOContact;
import domain.IContact;

/**
 * Servlet implementation class UpdateContactServlet
 */
public class UpdateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static IContact contact = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateContactServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idContact = StringUtil.getValue(request.getParameter("idContact"));
		
		if((contact == null) || (! idContact.equals(String.valueOf(contact.getId())))){
			System.out.println("contact is null");
			//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
			IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
			Object[] result = dao.getContactById(idContact);
			contact = (IContact)result[0];
			getServletContext().setAttribute("versionContactToUpdate", contact.getVersion());
		}
		
		System.out.println("listing : " + contact.getVersion());
		request.setAttribute("contact", contact);
		request.setAttribute("title", "Update Contact");
		RequestDispatcher rd = request.getRequestDispatcher("Views/Contact/UpdateContact.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		if(contact == null){
			request.setAttribute("titleErrors", "Failed to get contact");
			request.setAttribute("errors", "Contact not found");
			rd = request.getRequestDispatcher("Views/Contact/ListContact.jsp");
			rd.forward(request, response);
		}
		
		String id = StringUtil.getValue(request.getParameter("id"));
		String fname = StringUtil.getValue(request.getParameter("fname"));
		String lname = StringUtil.getValue(request.getParameter("lname"));
		String email = StringUtil.getValue(request.getParameter("email"));
		String street = StringUtil.getValue(request.getParameter("street"));
		String zip = StringUtil.getValue(request.getParameter("zip"));
		String city = StringUtil.getValue(request.getParameter("city"));
		String country = StringUtil.getValue(request.getParameter("country"));
		String home = StringUtil.getValue(request.getParameter("homepn"));
		String office = StringUtil.getValue(request.getParameter("officepn"));
		String mobile = StringUtil.getValue(request.getParameter("mobilepn"));
		String siretnum = StringUtil.getValue(request.getParameter("siretNum"));
		int numsiret = -1;
		if((siretnum != null) && (! siretnum.isEmpty()) && (! siretnum.equalsIgnoreCase("N/P"))){
			try{
				numsiret = Integer.parseInt(siretnum);
			} catch (NumberFormatException e){}
		}

		if(id.isEmpty() && fname.isEmpty() && lname.isEmpty() && email.isEmpty() &&
				street.isEmpty() && zip.isEmpty() && city.isEmpty() && country.isEmpty() &&
				home.isEmpty() && office.isEmpty() && mobile.isEmpty()){
			response.sendRedirect("Views/Contact/ListContact.jsp?action=update");
		}
		
		PrintWriter out = response.getWriter();
		
		//DAOContact dao = new DAOContact();
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		request.setAttribute("title", "Update Contact");
		
		if((int)getServletContext().getAttribute("versionContactToUpdate") == contact.getVersion()){
			if(dao.updateContact(contact, fname, lname, email, street, zip, city, country, home, office, mobile, numsiret)){
				request.setAttribute("titleResult", "Contact succesfully updated");
				request.setAttribute("result", "Contact has been updated");
			} else {
				request.setAttribute("titleErrors", "Failed to update contact");
				request.setAttribute("errors", "Failure when updating contact : " + fname + " , " + lname);	
			}
		} else {
			request.setAttribute("titleErrors", "Failed to update contact");
			request.setAttribute("errors", "The contact is modified in the meantime, please reload the web page");	
		}
		

		
		
		request.setAttribute("contact", contact);
		rd = request.getRequestDispatcher("Views/Contact/UpdateContact.jsp");
		rd.forward(request, response);
		
//		Object[] result = dao.getContactById(id);
//		if(result == null){
//			request.setAttribute("titleErrors", "Failed to get contact");
//			request.setAttribute("errors", "Contact " + id + " not found");
//			rd = request.getRequestDispatcher("Views/Contact/ListContact.jsp");
//		} else {
//			request.setAttribute("contact", result[0]);
//			rd = request.getRequestDispatcher("Views/Contact/UpdateContact.jsp");
//		}
//		rd.forward(request, response);
	}

}
