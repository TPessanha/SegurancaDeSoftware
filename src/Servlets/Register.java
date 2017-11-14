package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");


        //creating connection with the database
        Connection conn;
        try {
            conn = Util.dbUtil.dbConnection.getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            Util.Authenticator.create_account(name, pass1, pass2);
            out.println("Account created");
            RequestDispatcher rs = request.getRequestDispatcher("register.html");
            rs.include(request, response);
        } catch (Exception e) {
            out.println(e.getMessage());
            RequestDispatcher rs = request.getRequestDispatcher("register.html");
            rs.include(request, response);
        }
    }
}
