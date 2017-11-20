package Servlets;

import Exceptions.MyException;
import Resources.Account;
import Resources.Operation;
import Util.Authenticator;
import Util.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static Util.Constants.UNKNOWN_ERROR_MSG;

public class Login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		HttpSession session;
		try {
			Account acc = Authenticator.login(user, pass);
			session = request.getSession(false);
			if (session != null) {
				String prevAcc = (String) session.getAttribute("USER");
				if (prevAcc != null) {
					Authenticator.logout(Authenticator.get_account(prevAcc));
				}
				session.invalidate();
			}
			
			session = request.getSession();
			session.setAttribute("USER", acc.getUsername());
			session.setAttribute("PASS", acc.getPassword());
			session.setAttribute("SALT", acc.getSalt());
			session.setAttribute("ROLE", acc.getRole().toString());
			session.setAttribute("LOGGED_IN", String.valueOf(acc.isLoggedIn()));
			session.setAttribute("LOCKED", String.valueOf(acc.isLocked()));
			
			Storage.logOperation(user, Operation.LOGIN, true);
			response.sendRedirect("welcome.jsp");
			
		} catch (MyException e) {
			Storage.logOperation(user, Operation.LOGIN, false, e.getMessage());
			out.println(e.getHtmlMsg());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.println(UNKNOWN_ERROR_MSG);
		} finally {
			RequestDispatcher rs = request.getRequestDispatcher("login.html");
			rs.include(request, response);
		}
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rs = request.getRequestDispatcher("login.html");
		rs.include(request, response);
	}
}
