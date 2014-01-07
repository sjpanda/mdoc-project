package servlet.contact;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import util.ApplicationContextUtils;

import dao.IDAOContact;
import domain.IAddress;
import domain.IPhoneNumber;
import domain.impl.Address;
import domain.impl.PhoneNumber;

/**
 * Servlet implementation class GenerateContactsServlet
 */
public class GenerateContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateContactsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//generate contact

		try{
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
			IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
			if(dao.generateContacts()){
				request.setAttribute("titleResult", "Generate contact : Contacts succesfully added");
				request.setAttribute("result", "Contact has been added");
			} else {
				request.setAttribute("titleErrors", "Generate contact");
				request.setAttribute("errors", "Failed to generate contacts");
			}
		} catch(Exception e){
			request.setAttribute("titleErrors", "Generate contact");
			request.setAttribute("errors", "Failed to generate contacts");
		} finally {
			request.setAttribute("title", "Home");
			this.getServletContext().getRequestDispatcher("/Views/Main.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
