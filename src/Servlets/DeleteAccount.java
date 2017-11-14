package Servlets;

import Resources.Account;
import Resources.Role;
import Util.Authenticator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class DeleteAccount extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteAccount.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");

        try {
            Account acc = Authenticator.login(request, response);
            if (acc.getRole().equals(Role.ADMIN)) {
                Authenticator.delete_account(user);
                LOG.fine("User " + user + " deleted by" + acc.getUsername());

                out.println("Account (" + user + ") was deleted");
            }
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {
            RequestDispatcher rs = request.getRequestDispatcher("deleteAcc.jsp");
            rs.include(request, response);
        }
    }
}
