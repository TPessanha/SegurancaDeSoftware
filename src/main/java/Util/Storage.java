package Util;


import Resources.Account;
import Resources.Log;
import Resources.Operation;
import Util.dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Storage {
	
	public static boolean checkUser(String user) {
		return getAccount(user) != null;
	}
	
	static Account getAccount(String username) {
		try {
			Connection connection = dbConnection.getConnection();
			
			//Execute query
			PreparedStatement ps = connection.prepareStatement("select * from Accounts where Username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			//Get user
			String tmpTest = rs.getString("LoggedIn");
			if (rs.next()) {
				Account acc = new Account(rs.getString("Username"), rs.getString("Password"), rs.getString("Salt"), rs.getString("Role"), String.valueOf(rs.getBoolean("Locked")), String.valueOf(rs.getBoolean("LoggedIn")));
				
				ps.close();
				return acc;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * @param acc The account to add to persistent storage
	 */
	static void addAccount(Account acc) {
		try {
			Connection connection = dbConnection.getConnection();
			//Execute query
			PreparedStatement ps = connection.prepareStatement("insert into Accounts values(?,?,?,?,?,?)");
			
			ps.setString(1, acc.getUsername());
			ps.setString(2, acc.getPassword());
			ps.setString(3, acc.getRole().toString());
			ps.setString(4, acc.getSalt());
			ps.setBoolean(5, acc.isLoggedIn());
			ps.setBoolean(6, acc.isLocked());
			int i = ps.executeUpdate();
			ps.close();
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	static void updateAccount(Account acc) {
		String sql = "UPDATE Accounts SET Password = ?, Role = ?,Salt = ?,LoggedIn = ?,Locked = ? WHERE Username = ?";
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// set the corresponding param
			ps.setString(1, acc.getPassword());
			ps.setString(2, acc.getRole().toString());
			ps.setString(3, acc.getSalt());
			ps.setBoolean(4, acc.isLoggedIn());
			ps.setBoolean(5, acc.isLocked());
			ps.setString(6, acc.getUsername());
			// execute the update statement
			ps.executeUpdate();
			ps.close();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	static int removeAccount(String user) {
		String sql = "DELETE FROM Accounts WHERE Username = ?";
		int nAffectedRows = 0;
		try {
			Connection conn = dbConnection.getConnection();
			assert conn != null;
			PreparedStatement ps = conn.prepareStatement(sql);
			// set the corresponding param
			ps.setString(1, user);
			// execute the delete statement
			nAffectedRows = ps.executeUpdate();
			ps.close();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return nAffectedRows;
	}
	
	static int removeAccount(Account acc) {
		return removeAccount(acc.getUsername());
	}
	
	public static void logOperation(String username, Operation op, boolean success) {
		try {
			Connection connection = dbConnection.getConnection();
			
			//Execute query
			PreparedStatement ps = connection.prepareStatement("insert into Log(UsernameUsed, Operation, Success) values(?,?,?)");
			
			ps.setString(1, username);
			ps.setString(2, op.toString());
			ps.setBoolean(3, success);
			int i = ps.executeUpdate();
			ps.close();
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void logOperation(String username, Operation op, boolean success, String notes) {
		try {
			Connection connection = dbConnection.getConnection();
			
			//SQL query
			PreparedStatement ps = connection.prepareStatement("insert into Log(UsernameUsed, Operation, Success,Notes) values(?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, op.toString());
			ps.setBoolean(3, success);
			ps.setString(4, notes);
			
			int i = ps.executeUpdate();
			ps.close();
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	static List<Log> getLastLoinsAttempts(String username, int numberOfAttemps) {
		List<Log> logs = new ArrayList<>(10);
		try {
			Connection connection = dbConnection.getConnection();
			
			//Execute query
			PreparedStatement ps = connection.prepareStatement("select * from Log where UsernameUsed=? AND Operation = ? ORDER BY DateTime DESC;");
			ps.setString(1, username);
			ps.setString(2, Operation.LOGIN.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next() && logs.size() < numberOfAttemps) {
				Log log = new Log(
						rs.getString("UsernameUsed"),
						getDateTime(rs.getString("DateTime")),
						Operation.fromValue(rs.getString("Operation")),
						rs.getBoolean("Success"),
						rs.getString("Notes")
				);
				logs.add(log);
			}
			ps.close();
			return logs;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	/*public static Log getLastLoginAttemp(String username) {
		try {
			Connection connection = dbConnection.getConnection();
			
			//Execute query
			PreparedStatement ps = connection.prepareStatement("select * from Log LIMIT 1 where UsernameUsed=? && Operation = ?");
			ps.setString(1, username);
			ps.setString(2, Operation.LOGIN.toString());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Log log = new Log(
						rs.getString("UsernameUsed"),
						getDateTime(rs.getString("DateTime")),
						Operation.fromValue(rs.getString("Operation")),
						rs.getBoolean("Success"),
						rs.getString("Notes")
				);
				ps.close();
				return log;
			}
			ps.close();
			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}*/
	
	private static Date getDateTime(String timestamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = null;
		try {
			date = dateFormat.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assert date != null;
		return date;
	}
}
