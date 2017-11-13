package Util;


import Resources.Account;
import Resources.AccountsBook;
import Util.dbUtil.dbConnection;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import static Util.Constants.DATABASE_FILE_PATH;

public class Storage {
    private static final Logger LOG = Logger.getLogger(Storage.class.getName());

    /**
     * @return A list of all the accounts
     */
    public static ArrayList<Account> getAllAccounts() {
        Account acc;
        ArrayList<Account> list = new ArrayList<>();
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(DATABASE_FILE_PATH);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                list.add(new Account(line,
                        br.readLine(),
                        br.readLine(),
                        br.readLine(),
                        br.readLine(),
                        br.readLine()
                ));
            }
            return list;
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
    }

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
                if (rs.next()) {
                    LOG.fine("User found");
                    Account acc = new Account(
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Salt"),
                            rs.getString("Role"),
                            rs.getString("Locked"),
                            rs.getString("LoggedIn"));
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

                LOG.fine("Added (" + i + ") new accounts");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Saves all the accounts in persistant storage but deletes the previous ones
     *
     * @param book The book containing all the accounts to save
     */
    public static void SaveChangesToStorage(AccountsBook book) {
        FileWriter fw;
        PrintWriter pw;

        try {

            fw = new FileWriter(Paths.get("") + DATABASE_FILE_PATH);
            pw = new PrintWriter(fw);

            pw.write("");
            for (Account acc : book.getAllAccounts()) {
                pw.append(acc.getUsername()).append("\r\n")
                        .append(acc.getPassword()).append("\r\n")
                        .append(acc.getSalt()).append("\r\n")
                        .append(acc.getRole().toString()).append("\r\n")
                        .append(String.valueOf(acc.isLocked())).append("\r\n")
                        .append(String.valueOf(acc.isLoggedIn())).append("\r\n");
            }

            pw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
