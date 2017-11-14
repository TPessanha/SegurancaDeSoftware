package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import Resources.Account;
import Util.Authenticator;
import Util.Constants;
import Util.Storage;

public class Logout extends HttpServlet{
	
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
        try {
        	Account acc = Authenticator.login(request, response);
        	Authenticator.logout(acc);
        	LOG.fine("User logout (" + acc.getUsername() + ")");

        	HttpSession session = request.getSession(false);
        	session.invalidate();

            response.sendRedirect("login.html");
        } catch (Exception e) {
            out.println(e.getMessage());
		}
	}
}
