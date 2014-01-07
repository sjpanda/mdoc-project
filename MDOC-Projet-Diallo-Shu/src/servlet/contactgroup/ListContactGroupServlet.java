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

import util.ApplicationContextUtils;
import util.StringUtil;

import dao.IDAOContact;
import dao.IDAOContactGroup;

/**
 * Servlet implementation class ListContactGroupServlet
 */
public class ListContactGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListContactGroupServlet() {
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
		request.setAttribute("title", "List Contact Group");
		RequestDispatcher rd = request.getRequestDispatcher("Views/ContactGroup/ListContactGroup.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
