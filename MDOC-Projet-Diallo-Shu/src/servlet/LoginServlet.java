package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        RequestDispatcher rd;
        if(session.getAttribute("user") != null){
        	rd = request.getRequestDispatcher("Views/Main.jsp");
    		request.setAttribute("title", "Home");
        }
        else{
    		rd = request.getRequestDispatcher("Views/index.jsp");
    		request.setAttribute("title", "Log in");
        }
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = StringUtil.getValue(request.getParameter("name"));
		String password = StringUtil.getValue(request.getParameter("password"));
		
		RequestDispatcher rd;
		
		if(name.isEmpty() || password.isEmpty()){
			rd = request.getRequestDispatcher("Views/index.jsp");
			rd.forward(request, response);
			return;
		}
		
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
		
		if(name.equals(password)){
			session.setAttribute("user", name);
			rd = request.getRequestDispatcher("Views/Main.jsp");
		} else {
	        session.setAttribute("user", null);
			rd = request.getRequestDispatcher("Views/index.jsp");
		}
		rd.forward(request, response);
	}

}
