package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePassword extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Creating HTML page
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<center>");
		out.println("<h1>Change Account</h1>");
		out.println("<br>");
		if(req.getParameter("Message") != null)
			out.println(req.getParameter("Message"));
		
		out.print("<form action=\"");
		out.print("Authenticator\" ");
		out.println("method=POST>");
		out.println("Name: ");
		out.println("<input type=text size=20 name=name>");
		out.println("<br>");
		out.println("Password: ");
		out.println("<input type=password size=20 name=password>");
		out.println("<br>");
		out.println("Confirm Password: ");
		out.println("<input type=password size=20 name=confirmpassword>");
		out.println("<br>");
		out.println("<input type=submit name=changeAccountButton value='Change password'>");
		out.println("</form>");
		
		//Finishing HTML page and closing writer
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
