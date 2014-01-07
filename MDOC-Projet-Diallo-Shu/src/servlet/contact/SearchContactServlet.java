package servlet.contact;

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

import util.ApplicationContextUtils;
import util.StringUtil;
import dao.IDAOContact;
import domain.impl.Address;
import domain.IAddress;
import domain.IContact;

/**
 * Servlet implementation class SearchContactServlet
 */
public class SearchContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("Views/Contact/SearchContact.jsp");
		request.setAttribute("title", "Search Contact");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean toSearch = true;
		
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

		if(fname.isEmpty() && lname.isEmpty() && email.isEmpty() &&
				street.isEmpty() && zip.isEmpty() && city.isEmpty() && country.isEmpty() &&
				home.isEmpty() && office.isEmpty() && mobile.isEmpty()){
			toSearch = false;
		}
		
		request.setAttribute("title", "Search Contact");
		
		if(toSearch){
			PrintWriter out = response.getWriter();
			
			//DAOContact dao =  new DAOContact();
			//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
			IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
			
			
			//IAddress address = new Address(street, city, zip, country);
			IAddress address = (IAddress)ApplicationContextUtils.getApplicationContext().getBean("Address");
			address.setStreet(street);
			address.setCity(city);
			address.setZip(zip);
			address.setCountry(country);
			List contacts = dao.searchContact(fname, lname, email, address, home, office, mobile);
			if((contacts == null) || (contacts.isEmpty())){
				out.println("Contact not found");
			} else {	
				request.setAttribute("contacts", contacts);
				this.getServletContext().getRequestDispatcher("/Views/Contact/ResultSearchContact.jsp").forward(request, response);
			}
			
		}else {
			response.sendRedirect("Views/Contact/SearchContact.jsp");
		}
	}

}
