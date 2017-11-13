package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Creating HTML page
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<center>");
		out.println("<h1>Create Account</h1>");
		out.println("<br>");
		if(req.getParameter("Message") != null)
			out.println(req.getParameter("Message"));
		
		out.print("Are you sure you want to logout?");
		out.print("<form action=\"");
		out.print("Authenticator\" ");
		out.println("method=POST>");
		out.println("<input type=submit name=logoutButton value='Logout'>");
		out.println("</form>");
		
		out.print("<form action=\"");
		out.print("MainMenu\" ");
		out.println("method=GET>");
		out.println("<input type=submit name=cancelLogout value='Cancel'>");
		out.println("</form>");
		
		//Finishing HTML page and closing writer
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
