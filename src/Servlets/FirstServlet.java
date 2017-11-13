package Servlets;

import Resources.Account;
import Util.Constants;
import Util.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FirstServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Response type
        response.setContentType("text/html");

        //HTML
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World!!!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + Constants.DATABASE_FILE_PATH + "</h1>");


        //CODE
        Account acc = new Account("test", "test");
        Storage.addAccount(acc);
        Account acc2 = Storage.getAccount("test");
        out.println("<h3>"+acc.getUsername()+"</h3>");
        out.println("<h3>"+acc.getPassword()+"</h3>");
        out.println("<h3>"+acc.getSalt()+"</h3>");
        out.println("<h3>"+acc.getRole().toString()+"</h3>");
        out.println("<h3>"+acc.isLoggedIn()+"</h3>");
        out.println("<h3>"+acc.isLocked()+"</h3>");

        //HTML
        out.println("</body>");
        out.println("</html>");

    }
}