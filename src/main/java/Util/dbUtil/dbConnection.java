package Util.dbUtil;

import Exceptions.CouldNotConnectToDatabaseException;
import Resources.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Util.Constants.DATABASE_FILE_PATH;
import static Util.Constants.USE_ABSOLUTE_PATH;


public class dbConnection {
	public static Connection getConnection() throws SQLException, CouldNotConnectToDatabaseException {
		try {
			String SQCONN = "jdbc:sqlite:";
			if (USE_ABSOLUTE_PATH) {
				SQCONN += DATABASE_FILE_PATH;
			} else {
				SQCONN += Account.class.getClassLoader().getResource("/Database.db");
				SQCONN = SQCONN.replace("target/Seguranca/WEB-INF/classes/Database.db", DATABASE_FILE_PATH);
			}
			
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(SQCONN);
			if (conn == null) throw new CouldNotConnectToDatabaseException();
			return conn;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new CouldNotConnectToDatabaseException();
		}
	}
}
