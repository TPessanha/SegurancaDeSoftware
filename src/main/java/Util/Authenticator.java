package Util;

import Exceptions.*;
import Resources.Account;
import Resources.Log;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Authenticator {
	public static void create_account(String name, String pwd1, String pwd2) throws PasswordMismatchException, UsernameInUseException, PasswordTooWeakException, PasswordDoesNotMeetRequirementsException {
		if (!pwd1.equals(pwd2)) throw new PasswordMismatchException();
		if (Storage.getAccount(name.toLowerCase()) != null) throw new UsernameInUseException();
		
		runStrengthTest(pwd1);
		
		Account acc = new Account(name.toLowerCase(), pwd1);
		Storage.addAccount(acc);
	}
	
	private static void runStrengthTest(String pwd1) throws PasswordTooWeakException, PasswordDoesNotMeetRequirementsException {
		Strength str = getPasswordStrength(pwd1);
		if (str.getScore() < Constants.MIN_PASSWORD_SECURITY_SCORE) {
			List<String> lines = new ArrayList<String>(10);
			
			lines.add("Password is too weak, strength (" + str.getScore() + ") from 0-4.");
			lines.add("Time to crack: " + str.getCrackTimesDisplay().getOfflineSlowHashing1e4perSecond());
			lines.add(str.getFeedback().getWarning());
			lines.add("Suggestions:");
			lines.addAll(str.getFeedback().getSuggestions());
			
			throw new PasswordTooWeakException("Password is too weak", lines);
		}
		if (pwd1.length() > 50)
			throw new PasswordDoesNotMeetRequirementsException("Password max length is 50 characters");
		
	}
	
	public static void delete_account(String name) throws UndefinedAccountException {
		if (Storage.removeAccount(name.toLowerCase()) == 0) throw new UndefinedAccountException();
	}
	
	public static void change_pwd(String name, String pwd1, String pwd2) throws UndefinedAccountException, PasswordMismatchException, PasswordTooWeakException, PasswordDoesNotMeetRequirementsException {
		if (!pwd1.equals(pwd2)) throw new PasswordMismatchException();
		
		Account acc = Storage.getAccount(name.toLowerCase());
		if (acc == null) throw new UndefinedAccountException();
		
		runStrengthTest(pwd1);
		
		acc.setPassword(pwd1);
		Storage.updateAccount(acc);
	}
	
	public static Account login(String name, String pwd) throws LockedAccountException, AuthenticationErrorException, UndefinedAccountException {
		Account acc = get_account(name.toLowerCase());
		if (acc.isLocked()) {
			//TODO Check to see if its free again
			List<Log> logs = Storage.getLastLoginsAttemps(acc.getUsername(), 20);
			int failedAttempts = 0;
			for (Log l : logs) {
				if (!l.isSuccess())
					failedAttempts++;
				else
					break;
			}
			Log lastLogin = logs.get(0);
			Date now = new Date();
			Calendar cal = Calendar.getInstance(); // creates calendar
			cal.setTime(lastLogin.getDateTime()); // sets calendar time/date
			cal.add(Calendar.MINUTE, getDelay(failedAttempts)); // adds one hour
			Date test = cal.getTime();
			
			if (now.after(cal.getTime())) {
				acc.setLocked(false);
				Storage.updateAccount(acc);
			} else
				throw new LockedAccountException();
		} else {
			List<Log> logs = Storage.getLastLoginsAttemps(acc.getUsername(), 20);
			int failedAttempts = 0;
			for (Log l : logs) {
				if (!l.isSuccess())
					failedAttempts++;
				else
					break;
			}
			if (failedAttempts > 5) {
				acc.setLocked(true);
				Storage.updateAccount(acc);
				throw new LockedAccountException();
			}
		}
		
		if (CryptoUtil.doesPasswordMatch(pwd, acc.getPassword())) {
			
			acc.setLoggedIn(true);
			Storage.updateAccount(acc);
			return acc;
		} else {
			throw new AuthenticationErrorException("Invalid password", "Invalid userID or password");
		}
	}
	
	private static int getDelay(int failedAttempts) {
		if (failedAttempts < 10)
			return Constants.LVL1_LOGIN_DELAY;
		else if (failedAttempts < 13)
			return Constants.LVL2_LOGIN_DELAY;
		else if (failedAttempts < 16)
			return Constants.LVL3_LOGIN_DELAY;
		else
			return Constants.MAX_LOGIN_DELAY;
		
	}
	
	public static void logout(Account acc) throws UndefinedAccountException {
		get_account(acc.getUsername());
		acc.setLoggedIn(false);
		Storage.updateAccount(acc);
	}
	
	public static Account get_account(String name) throws UndefinedAccountException {
		Account acc = Storage.getAccount(name.toLowerCase());
		if (acc == null) throw new UndefinedAccountException();
		return acc;
	}
	
	public static Account login(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationErrorException {
		Account acc;
		try {
			acc = new Account(req.getSession().getAttribute("USER").toString(), req.getSession().getAttribute("PASS").toString(), req.getSession().getAttribute("SALT").toString(), req.getSession().getAttribute("ROLE").toString(), req.getSession().getAttribute("LOCKED").toString(), req.getSession().getAttribute("LOGGED_IN").toString());
			Account acc2 = get_account(acc.getUsername());
			if (!acc.equals(acc2)) throw new AuthenticationErrorException();
		} catch (Exception e) {
			throw new AuthenticationErrorException();
		}
		return acc;
	}
	
	private static Strength getPasswordStrength(String password) {
		Zxcvbn passTester = new Zxcvbn();
		return (passTester.measure(password));
	}
}
