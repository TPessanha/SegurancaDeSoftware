package Servlets;

import Resources.Account;
import Resources.AccountsBook;
import Resources.Storage;
import Util.Constants;

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
		out.println("</body>");
		out.println("</html>");
		
		//CODE
		Account acc = new Account("test", "test");
		AccountsBook book = new AccountsBook();
		book.addAccount(acc);
		book.addAccount(new Account("test1", "test"));
		Storage.SaveChangesToStorage(book);
		book.loadAccounts();
	}
}