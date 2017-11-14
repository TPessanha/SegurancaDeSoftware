package Util;


import Resources.Account;
import Util.dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Storage {
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());

    public static boolean checkUser(String user) {
        if (getAccount(user) == null)
            return false;
        return true;
    }

    public static Account getAccount(String username) {
        try {
            Connection connection = dbConnection.getConnection();
            if (connection != null) {

                //Execute query
                PreparedStatement ps = connection.prepareStatement
                        ("select * from Accounts where Username=?");
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                //Get user
                String tmpTest = rs.getString("LoggedIn");
                if (rs.next()) {
                    LOG.fine("User found");
                    Account acc = new Account(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Salt"),
                            rs.getString("Role"),
                            String.valueOf(rs.getBoolean("Locked")),
                            String.valueOf(rs.getBoolean("LoggedIn")));

                    ps.close();
                    return acc;
                }
                LOG.fine("User NOT found");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param acc The account to add to persistent storage
     */
    public static void addAccount(Account acc) {
        try {
            Connection connection = dbConnection.getConnection();
            if (connection != null) {

                if (checkUser(acc.getUsername())) {
                    LOG.warning("Username already in use");
                    return;
                }

                //Execute query
                PreparedStatement ps = connection.prepareStatement
                        ("insert into Accounts values(?,?,?,?,?,?)");

                ps.setString(1, acc.getUsername());
                ps.setString(2, acc.getPassword());
                ps.setString(3, acc.getRole().toString());
                ps.setString(4, acc.getSalt());
                ps.setBoolean(5, acc.isLoggedIn());
                ps.setBoolean(6, acc.isLocked());
                int i = ps.executeUpdate();
                ps.close();

                LOG.fine("Added (" + i + ") new accounts");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void updateAccount(Account acc) {
        String sql = "UPDATE Accounts SET Password = ?, Role = ?,Salt = ?,LoggedIn = ?,Locked = ? WHERE Username = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // set the corresponding param
            ps.setString(1, acc.getPassword());
            ps.setString(2, acc.getRole().toString());
            ps.setString(3, acc.getSalt());
            ps.setBoolean(4, acc.isLoggedIn());
            ps.setBoolean(5, acc.isLocked());
            ps.setString(6, acc.getUsername());
            // execute the update statement
            int i = ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void removeAccount(String user) {
        String sql = "DELETE FROM Accounts WHERE Username = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // set the corresponding param
            ps.setString(1, user);
            // execute the delete statement
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void removeAccount(Account acc) {
        removeAccount(acc.getUsername());
    }

}
