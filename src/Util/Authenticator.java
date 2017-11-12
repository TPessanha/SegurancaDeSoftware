package Util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Resources.Account;
import Resources.AccountsBook;

public class Authenticator extends HttpServlet{
	
	private AccountsBook accountsBook = new AccountsBook();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		if(req.getParameter("loginButton") != null) {
			try {
				String name = req.getParameter("name");
				String password = req.getParameter("password");
				HttpSession session = req.getSession(true);
				Account authUser = login(name, password);
				//TODO session.setAttribute(JWT, authUser.getJWT());
				resp.sendRedirect("MainMenu");
			} catch (AuthenticationErrorException e) {
				resp.sendRedirect("MainPage");
			} catch (LockedAccountException e) {
				resp.sendRedirect("MainPage");
			} catch (UndefinedAccountException e) {
				resp.sendRedirect("MainPage");
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
		if(pwd1.equals(pwd2)) {
			new Account(name, pwd1);
			System.out.println("create_account finish.");
		}
	}

	/**
	 * Deletes an existing account.
	 * Precondition: Cannot be logged in
	 * Precondition: Account must be locked
	 * @param name account name.
	 */
	private void delete_account(String name) {
		System.out.println("delete_account finish.");
	}

	/**
	 * Gets a readonly clone of an existing account
	 * @param name account name.
	 * @return readonly clone of Account.
	 */
	private Account get_account(String name) {
		System.out.println("get_account finish.");
		return null;
	}

	/**
	 * Changes password of the account to pwd1.
	 * @param name account name.
	 * @param pwd1 first password input.
	 * @param pwd2 second password input.
	 */
	private void change_pwd(String name, String pwd1, String pwd2) {
		System.out.println("change_pwd finish.");
	}


	private Account login(String name, String pwd) throws UndefinedAccountException, LockedAccountException, AuthenticationErrorException {
		Account account = accountsBook.getAccount(name);
		if(account == null)
			throw new UndefinedAccountException();
		//TODO boolean isLocked = account.isLocked();
		boolean isLocked = false;
		if(isLocked)
			throw new LockedAccountException();
		//TODO check how to hash password
		if(!account.getPassword().equals(pwd.hashCode()))
			throw new AuthenticationErrorException();
		//TODO Set locked with: account.setLocked()
		System.out.println("login finish.");
		return account;
	}

	/**
	 * 
	 * @param acc
	 */
	private void logout(Account acc) {
		System.out.println("logout finish.");
	}
	/**
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	private Account login(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("login finish.");
		return null;
	}
}

class UndefinedAccountException extends Exception{

}

class LockedAccountException extends Exception{

}

class AuthenticationErrorException extends Exception{

}