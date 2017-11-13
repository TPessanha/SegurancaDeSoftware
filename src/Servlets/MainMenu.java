package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainMenu extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.println("<center>");
		out.println("<h1>Main Page</h1>");
		out.println("<br>");
		out.println("<br>");

		//Create Account button
		out.print("<form action=\"");
		out.print("CreateAccount\" ");
		out.println("method=POST>");
		out.println("<input type=submit name=createAccountPage value='Create new account'>");
		out.println("</form>");
		
		//Delete Account button
		out.print("<form action=\"");
		out.print("DeleteAccount\" ");
		out.println("method=POST>");
		out.println("<input type=submit name=deleteAccountPage value='Delete existing account'>");
		out.println("</form>");

		//Change Password
		out.print("<form action=\"");
		out.print("ChangePassword\" ");
		out.println("method=POST>");
		out.println("<input type=submit name=changeAccountPage value='Change existing account password'>");
		out.println("</form>");
		
		//Logout
		out.print("<form action=\"");
		out.print("Logout\" ");
		out.println("method=POST>");
		out.println("<input type=submit name=logoutAccount value='Logout'>");
		out.println("</form>");
		
		//Finishing HTML page and closing writer
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}	
}
