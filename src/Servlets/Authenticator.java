package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Resources.Account;
import Resources.AccountsBook;

public class Authenticator extends HttpServlet{

	AccountsBook accountsBook = new AccountsBook();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		PrintWriter out = resp.getWriter();
		Account root = new Account("root", "root");
		accountsBook.addAccount(root);
		resp.setContentType("text/html");
		out.println("<br><br>");
		// Login
		if(req.getParameter("loginButton") != null) {
			try {
				String name = req.getParameter("name");
				String password = req.getParameter("password");
				HttpSession session = req.getSession(true);
				Account authUser = login(name, password, out);
				//TODO session.setAttribute(JWT, authUser.getJWT());
				resp.sendRedirect("MainMenu");
			} catch (AuthenticationErrorException e) {
				out.println("Authentication error!");
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			} catch (LockedAccountException e) {
				out.println("Account is locked!");
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			} catch (UndefinedAccountException e) {
				out.println("Account not found!!");
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			} catch (Exception e) {
				out.println("Undefined Exception <br> ");
				e.printStackTrace(out);
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			}
		}
		else if(req.getParameter("createAccountButton") != null) {
			try{
				create_account(req.getParameter("name"),
						req.getParameter("password"),
						req.getParameter("confirmpassword"));
				out.println("<input type=hidden name=confirmpassword value'Password changed'>");
			} catch (NoPasswordMatchException e) {
				out.println("<input type=hidden name=Message value'ERROR: Passwords did not match!'>");
			} finally {
				resp.sendRedirect("MainMenu");
			}
		} // Logout
		else if(req.getParameter("logoutButton") != null) {
			try {
				Account authUser = login(req, resp);
				logout(authUser);
				HttpSession session = req.getSession(false);
				if(session != null) session.invalidate();
				out.println("<input type=hidden name=Message value'Logged out'>");
				resp.sendRedirect("MainPage");
			} catch (Exception e) {
				out.println("Undefined Exception <br> ");
				e.printStackTrace(out);
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			}
		} // Authenticate (2nd login operation)
		else if(req.getParameter("authenticate") != null) {
			try {
				Account authUser = login(req, resp);
				//TODO logger and redirect to true operation
				resp.sendRedirect("MainMenu");
			} catch (Exception e) {
				out.println("Undefined Exception <br> ");
				e.printStackTrace(out);
				out.println("<br>");
				out.println("<a href='MainPage'>MainPage</a>");
			}
		}
		
		//Finishing HTML page and closing writer
		out.println("</body>");
		out.println("</html>");
		out.close();
	};

	/**
	 * Creates a new Account. PWD1 must be the same as PWD2.
	 * @param name account name.
	 * @param pwd1 first password input.
	 * @param pwd2 second password input.
	 * @throws NoPasswordMatchException 
	 */
	private void create_account(String name, String pwd1, String pwd2) throws NoPasswordMatchException {
		if(pwd1.equals(pwd2))
			accountsBook.addAccount(new Account(name, pwd1));
		else throw new NoPasswordMatchException();

	}

	/**
	 * Deletes an existing account.
	 * Precondition: Cannot be logged in
	 * Precondition: Account must be locked
	 * @param name account name.
	 */
	private void delete_account(String name) {
		//Check if not logged in
		//Lock account so no one will authenticate during the operation
		accountsBook.deleteAccount(name);
	}

	/**
	 * Gets a readonly clone of an existing account
	 * @param name account name.
	 * @return readonly clone of Account.
	 */
	private Account get_account(String name) {
		return null;
	}

	/**
	 * Changes password of the account to pwd1.
	 * @param name account name.
	 * @param pwd1 first password input.
	 * @param pwd2 second password input.
	 * @throws NoPasswordMatchException 
	 */
	private void change_pwd(String name, String pwd1, String pwd2) throws NoPasswordMatchException {
		if(pwd1.equals(pwd2))
			accountsBook.getAccount(name).setPassword(pwd1);
		else throw new NoPasswordMatchException();
	}


	private Account login(String name, String pwd, PrintWriter out) throws UndefinedAccountException, LockedAccountException, AuthenticationErrorException {
		out.println("1 <br>");
		Account account = accountsBook.getAccount(name);
		out.println("2 <br>");
		if(account == null)
			throw new UndefinedAccountException();
		out.println("3 <br>");
		if(account.isLocked())
			throw new LockedAccountException();
		//TODO check how to hash password
		out.println("4 <br>");
		if(!account.checkPassword(pwd)) {
			throw new AuthenticationErrorException();
		}
		out.println("5 <br>");
		account.setLoggedIn(true);
		out.println("6 <br>");
		return account;
	}

	/**
	 * Sets account has not being logged in.
	 * @param acc
	 */
	private void logout(Account acc) {

	}
	/**
	 * Authenticate the caller in every servlet interaction, supposed to be logged in already!
	 * @param req
	 * @param res
	 * @return
	 */
	private Account login(HttpServletRequest req, HttpServletResponse res) throws AuthenticationErrorException {
		return null;
	}
}

class NoPasswordMatchException extends Exception{
	
}

class UndefinedAccountException extends Exception{

}

class LockedAccountException extends Exception{

}

class AuthenticationErrorException extends Exception{

}