package servlet.contact;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

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
import domain.IAddress;
import domain.IPhoneNumber;
import domain.impl.Address;
import domain.impl.PhoneNumber;

/**
 * Servlet implementation class CreateContactServlet
 */
public class CreateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Create Contact");
		RequestDispatcher rd = request.getRequestDispatcher("Views/Contact/CreateContact.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String siretNum = StringUtil.getValue(request.getParameter("siretNum"));
		int numSiret = -1;
		if(! siretNum.isEmpty()){
			try{
				numSiret = Integer.parseInt(siretNum);
			} catch(NumberFormatException e) {
				response.sendRedirect("CreateContactServlet");
				return;
			}
		}

		String fname = StringUtil.getValue(request.getParameter("fname"));
		String lname = StringUtil.getValue(request.getParameter("lname"));
		String email = StringUtil.getValue(request.getParameter("email"));

		if(fname.isEmpty() || lname.isEmpty() || email.isEmpty()){
			response.sendRedirect("Views/Contact/CreateContact.jsp");
		}
		
		String street = StringUtil.getValue(request.getParameter("street"));
		String zip = StringUtil.getValue(request.getParameter("zip"));
		String city = StringUtil.getValue(request.getParameter("city"));
		String country = StringUtil.getValue(request.getParameter("country"));
		
		IAddress address;
		if(street.isEmpty() && zip.isEmpty() && city.isEmpty() && country.isEmpty()){
			address = null;
		} else {
			//address = new Address(street, city, zip, country);
			address = (IAddress)ApplicationContextUtils.getApplicationContext().getBean("Address");
			address.setStreet(street);
			address.setCity(city);
			address.setZip(zip);
			address.setCountry(country);
		}
		
		String homepn = StringUtil.getValue(request.getParameter("homepn"));
		String officepn = StringUtil.getValue(request.getParameter("officepn"));
		String mobilepn = StringUtil.getValue(request.getParameter("mobilepn"));
		
		Set<IPhoneNumber> profiles;
		if(homepn.isEmpty() && officepn.isEmpty() && mobilepn.isEmpty()){
			profiles = null;
		} else {
			profiles = new HashSet<IPhoneNumber>();
			if(! homepn.isEmpty()){
				//IPhoneNumber home = new PhoneNumber("home", homepn);
				IPhoneNumber home = (IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumber");
				home.setPhoneKind("home");
				home.setPhoneNumber(homepn);
				profiles.add(home);
			}
			if(! officepn.isEmpty()){
				//IPhoneNumber office = new PhoneNumber("office", officepn);
				IPhoneNumber office = (IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumber");
				office.setPhoneKind("office");
				office.setPhoneNumber(officepn);
				profiles.add(office);
			}
			if(! mobilepn.isEmpty()){
				//IPhoneNumber mobile = new PhoneNumber("mobile", mobilepn);
				IPhoneNumber mobile = (IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumber");
				mobile.setPhoneKind("mobile");
				mobile.setPhoneNumber(mobilepn);
				profiles.add(mobile);
			}
		}
		
		PrintWriter out = response.getWriter();
		
		//DAOContact dao =  new DAOContact();
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ApplicationContext context = ApplicationContextUtils.getApplicationContext(getServletContext());
		IDAOContact dao = (IDAOContact)context.getBean("DAOContact");
		
		if(dao.createContact(fname, lname, email, address, profiles, numSiret)){
			request.setAttribute("titleResult", "Contact succesfully created");
			request.setAttribute("result", "Contact has been created");
		} else {
			request.setAttribute("titleErrors", "Failed to create contact");
			request.setAttribute("errors", "Failure when creating contact : " + fname + " , " + lname);
		}
		
		this.doGet(request, response);
		
	}

}
