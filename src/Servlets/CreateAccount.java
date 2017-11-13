package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Resources.Account;
import Util.Authenticator;
import Util.Storage;

public class CreateAccount extends HttpServlet{
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");
        
        try {
        	Account acc = Authenticator.login(request, response);
        	Authenticator.create_account(user, pass1, pass2);
            LOG.fine("User " + user + " created by" + acc.getUsername());

            RequestDispatcher rs = request.getRequestDispatcher("welcome.html");
            rs.forward(request, response);
        } catch (Exception e) {
        	out.print(e.getMessage());
        }

	}
}
