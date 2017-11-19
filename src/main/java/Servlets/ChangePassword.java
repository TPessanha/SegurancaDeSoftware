package Servlets;

import Exceptions.MyException;
import Resources.Account;
import Resources.Operation;
import Util.Authenticator;
import Util.Constants;
import Util.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import static Util.Constants.UNKNOWN_ERROR_MSG;

public class ChangePassword extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		
		String user = (String) session.getAttribute(Constants.USER_COOKIE);
		String pass1 = request.getParameter("pass1");
		String pass2 = request.getParameter("pass2");
		Account acc = null;
		try {
			acc = Authenticator.login(request, response);
			Authenticator.change_pwd(user, pass1, pass2);
			Account accUpdated = Authenticator.get_account(user);
			session.setAttribute("PASS", accUpdated.getPassword());
			session.setAttribute("SALT", accUpdated.getSalt());
			
			Storage.logOperation(acc.getUsername(), Operation.CHANGE_PASSWORD, true);
			out.print("Password changed");
		} catch (MyException e) {
			out.print(e.getHtmlMsg());
			Storage.logOperation(acc.getUsername(), Operation.CHANGE_PASSWORD, false, "Reason: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.println(UNKNOWN_ERROR_MSG);
		} finally {
			RequestDispatcher rs = request.getRequestDispatcher("changePass.jsp");
			rs.include(request, response);
		}
	}
}
