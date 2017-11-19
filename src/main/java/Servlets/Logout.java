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

public class Logout extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Account acc = null;
		try {
			acc = Authenticator.login(request, response);
			Authenticator.logout(acc);
			
			HttpSession session = request.getSession(false);
			session.invalidate();
			
			Storage.logOperation(acc.getUsername(), Operation.LOGOUT, true);
		} catch (MyException e) {
			out.println(e.getHtmlMsg());
			Storage.logOperation(acc.getUsername(), Operation.LOGOUT, false, "Reason: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.println(Constants.UNKNOWN_ERROR_MSG);
		} finally {
			RequestDispatcher rs = request.getRequestDispatcher("login.html");
			rs.include(request, response);
		}
	}
}
