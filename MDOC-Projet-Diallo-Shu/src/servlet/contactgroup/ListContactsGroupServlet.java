package servlet.contactgroup;

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
 * Servlet implementation class ListContactsGroupServlet
 */
public class ListContactsGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListContactsGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
			IDAOContactGroup dao = (IDAOContactGroup)context.getBean("DAOContactGroup");
			List contactGroups = dao.getContactGroupByIdContactGroup(request.getParameter("idContactGroup"));
			request.setAttribute("contactGroups", contactGroups);
			
			request.setAttribute("title", "List Contacts");
			RequestDispatcher rd = request.getRequestDispatcher("Views/ContactGroup/ListContactsGroup.jsp");
			rd.forward(request, response);
		}catch(Exception e){			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
