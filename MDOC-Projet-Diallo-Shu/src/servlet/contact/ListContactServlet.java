package servlet.contact;

import java.io.IOException;
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
 * Servlet implementation class ListContactServlet
 */
public class ListContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListContactServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String action = StringUtil.getValue(request.getParameter("action"));
			if(action.equals("list")){
				request.setAttribute("title", "List Contact");
			}
			else{
				request.setAttribute("title", "Update Contact");
			}
			
			//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
			IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
			List contacts = dao.getAllContacts();
			request.setAttribute("contacts", contacts);
			
			RequestDispatcher rd = request.getRequestDispatcher("Views/Contact/ListContact.jsp");
			rd.forward(request, response);	
		}catch(Exception e){
			System.out.println("Erreur ListContactServlet doGet");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
