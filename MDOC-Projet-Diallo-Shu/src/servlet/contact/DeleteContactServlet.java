package servlet.contact;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.ApplicationContextUtils;
import util.StringUtil;

import dao.IDAOContact;
import dao.IDAOContactGroup;

/**
 * Servlet implementation class DeleteContactServlet
 */
public class DeleteContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/Views/Contact/ListContact.jsp?action=update";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteContactServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = StringUtil.getValue(request.getParameter("idContact"));
		
		if(id.isEmpty()){
			response.sendRedirect("Views/Contact/ListContact.jsp?action=delete");
		}
		PrintWriter out = response.getWriter();
		
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		
		
		if(dao.deleteContact(id)){
			request.setAttribute("titleResult", "Contact succesfully deleted");
			request.setAttribute("result", "Contact has been deleted");
		} else {
			request.setAttribute("titleErrors", "Failed to delete contact");
			request.setAttribute("errors", "Failure when deleting contact");
		}
		List contacts = dao.getAllContacts();
		request.setAttribute("contacts", contacts);
		request.setAttribute("title", "Update Contact");
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
