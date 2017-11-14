package Servlets;

import Resources.Account;
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

public class ChangePassword extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Storage.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        String user = (String) session.getAttribute(Constants.USER_COOKIE);
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");

        try {
            Account acc = Authenticator.login(request, response);
            Authenticator.change_pwd(user, pass1, pass2);
            Account accUpdated = Authenticator.get_account(user);
            session.setAttribute("PASS", accUpdated.getPassword());
            session.setAttribute("SALT", accUpdated.getSalt());
            LOG.fine("Changed password of " + user);


            out.print("Password changed");
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {
            RequestDispatcher rs = request.getRequestDispatcher("changePass.jsp");
            rs.include(request, response);
        }
    }
}
