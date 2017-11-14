package Servlets;

import Resources.Account;
import Util.Authenticator;
import Util.Constants;
import Util.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/Servlets/Login.java")
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

            HttpSession session = request.getSession();
            session.setAttribute("USER", acc.getUsername());
            session.setAttribute("PASS", acc.getPassword());
            session.setAttribute("SALT", acc.getSalt());
            session.setAttribute("ROLE", acc.getRole().toString());
            session.setAttribute("LOGGED_IN", String.valueOf(acc.isLoggedIn()));
            session.setAttribute("LOCKED", String.valueOf(acc.isLocked()));


          /*  //Add username cookie
            Cookie loginCookie = new Cookie(Constants.USER_COOKIE,user);
            //setting cookie to expiry in 2H
            loginCookie.setMaxAge((int) Constants.EXPIRATION_TIME);
            response.addCookie(loginCookie);*/

            response.sendRedirect("welcome.jsp");

        } catch (Exception e) {
            out.println(e.getMessage());
            RequestDispatcher rs = request.getRequestDispatcher("login.html");
            rs.include(request, response);
        }
        out.close();

    }

    public void destroy() {
        // release resources here
        super.destroy();
    }
}
