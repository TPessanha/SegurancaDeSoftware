package Servlets;

import Exceptions.MyException;
import Resources.AccessOperation;
import Resources.Account;
import Resources.Operation;
import Util.AccessControlCapabilities;
import Util.Authenticator;
import Util.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static Util.Constants.UNKNOWN_ERROR_MSG;

public class Register extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("user");
		String pass1 = request.getParameter("pass1");
		String pass2 = request.getParameter("pass2");
		Account acc = null;
		try {
			acc = Authenticator.login(request, response);
			if (AccessControlCapabilities.checkPermission(acc.getCapabilities(), "WebApp.Users", AccessOperation.CREATE)) {
				Util.Authenticator.create_account(name, pass1, pass2);
				out.println("Account created");
				Storage.logOperation(acc.getUsername(), Operation.REGISTER_USER, true, "Created a new user: " + name);
			} else {
				out.println("Permission denied");
				Storage.logOperation(acc.getUsername(), Operation.REGISTER_USER, false, "Permission denied");
			}
		} catch (MyException e) {
			out.println(e.getHtmlMsg());
			assert acc != null;
			Storage.logOperation(acc.getUsername(), Operation.REGISTER_USER, false, e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.println(UNKNOWN_ERROR_MSG);
		} finally {
			RequestDispatcher rs = request.getRequestDispatcher("register.jsp");
			rs.include(request, response);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rs = request.getRequestDispatcher("register.jsp");
		rs.include(request, response);
	}
}
