package Servlets;

import Resources.Account;
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
import java.util.logging.Logger;

public class Login extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        try {
            Account acc = Authenticator.login(user, pass);
            LOG.fine("User login (" + acc.getUsername() + ")");

            HttpSession session = request.getSession(true);
            session.setAttribute("USER", acc.getUsername());
            session.setAttribute("PASS", acc.getPassword());

            //Login
            out.println("<h1>Welcome " + acc.getUsername() + "</h1>");
            RequestDispatcher rs = request.getRequestDispatcher("welcome.html");
            rs.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage());
            RequestDispatcher rs = request.getRequestDispatcher("login.html");
            rs.include(request, response);
        }

    }
}
