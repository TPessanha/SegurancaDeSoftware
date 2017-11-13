package Util.dbUtil;

import Util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {
    private static final String SQCONN = "jdbc:sqlite:"+ Constants.DATABASE_FILE_PATH+"src\\Database.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
