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
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		PrintWriter out = resp.getWriter();
		Account root = new Account("root", "root");
		accountsBook.addAccount(root);
		out.print("Root username = " + root.getUsername() + " " + "Password = " + root.getPassword());
		out.println("<br><br>");
		if(req.getParameter("loginButton") != null) {
			resp.setContentType("text/html");
			try {
				out.println("Entered try block <br>");
				String name = req.getParameter("name");
				out.println("Got name " + name + " <br>");
				String password = req.getParameter("password");
				out.println("Got password " + password + " <br>");
				HttpSession session = req.getSession(true);
				out.println("Got session <br>");
				Account authUser = login(name, password, out);
				out.println("Logged in <br>");
				//TODO session.setAttribute(JWT, authUser.getJWT());
				//TODO Page MainMenu does not exist yet resp.sendRedirect("MainPage");
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
			}
		}
	};
	
	/**
	 * Creates a new Account. PWD1 must be the same as PWD2.
	 * @param name account name.
	 * @param pwd1 first password input.
	 * @param pwd2 second password input.
	 */
	private void create_account(String name, String pwd1, String pwd2) {
		if(pwd1.equals(pwd2))
			accountsBook.addAccount(new Account(name, pwd1));
	}

	/**
	 * Deletes an existing account.
	 * Precondition: Cannot be logged in
	 * Precondition: Account must be locked
	 * @param name account name.
	 */
	private void delete_account(String name) {
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
	 */
	private void change_pwd(String name, String pwd1, String pwd2) {
	}


	private Account login(String name, String pwd, PrintWriter out) throws UndefinedAccountException, LockedAccountException, AuthenticationErrorException {
		Account account = accountsBook.getAccount(name);
		out.print("<br> Acount = " + account.getUsername() + " + trying " + name + "<br>");
		out.println("<br> does userName exist?");
		if(account.getUsername().equals(""))
			throw new UndefinedAccountException();
		out.println("<br> yes.");
		//TODO boolean isLocked = account.isLocked();
		boolean isLocked = false;
		out.println("<br> is userName Locked?");
		if(isLocked)
			throw new LockedAccountException();
		out.println("<br> no.");
		//TODO check how to hash password
		out.println("<br> is password correct?");
		if(!account.checkPassword(pwd)) {
			out.println("<br> Saved account: " + account.getPassword());
			out.println("<br> Logged Aaccount: " + pwd);
			out.println("<br> Pw compare result: " + account.checkPassword(pwd));
			throw new AuthenticationErrorException();
		}
		out.println("<br> yes.");
		//TODO Set logged with: account.setLoggedIn()
		return account;
	}

	/**
	 * 
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

class UndefinedAccountException extends Exception{

}

class LockedAccountException extends Exception{

}

class AuthenticationErrorException extends Exception{

}