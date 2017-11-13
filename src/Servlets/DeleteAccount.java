package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Resources.Account;
import Util.Authenticator;
import Util.Storage;

public class DeleteAccount extends HttpServlet{
	
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String user = request.getParameter("user");

        Connection conn;
        try {
        	conn = Util.dbUtil.dbConnection.getConnection();
        } catch (Exception e) {
			out.print(e.getMessage());
		}
        
        try {
			Account acc = Authenticator.login(request, response);
			Authenticator.delete_account(user);
            LOG.fine("User " + user + " deleted by" + acc.getUsername());
            
            RequestDispatcher rs = request.getRequestDispatcher("welcome.html");
            rs.forward(request, response);
		} catch (Exception e) {
			out.print(e.getMessage());
		}
	}
}
