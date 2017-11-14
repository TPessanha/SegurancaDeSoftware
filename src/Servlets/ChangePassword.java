package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Exceptions.PasswordMismatch;
import Resources.Account;
import Util.Authenticator;
import Util.Storage;

public class ChangePassword extends HttpServlet{

    private static final Logger LOG = Logger.getLogger(Storage.class.getName());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        
        try {
        	Account acc = Authenticator.login(request, response);
        	Authenticator.change_pwd(user, pass1, pass2);
        	LOG.fine("Changed password of " + user + " by command of " + acc.getUsername());

            response.sendRedirect("welcome.jsp");
            
        }catch (Exception e) {
        	out.println(e.getMessage());
		}
	}
}
