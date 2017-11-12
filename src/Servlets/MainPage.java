package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPage extends HttpServlet{

	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		out.println("<h1><center>Main Page</center></h1>");
		out.println("<br>");
		out.println("<br>");

		//Login
		out.print("<form action=\"");
		out.print("Authenticator\" ");
		out.println("method=POST>");
		out.println("Name: ");
		out.println("<input type=text size=20 name=name>");
		out.println("<br>");
		out.println("Password: ");
		out.println("<input type=text size=20 name=password>");
		out.println("<br>");
		out.println("<input type=submit name=loginButton value='Login'>");
		out.println("</form>");

		//Finishing HTML page and closing writer
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
