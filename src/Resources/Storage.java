package Resources;


import java.io.*;
import java.util.ArrayList;

import static Util.Constants.FILE_NAME;

/**
 * Created by tomas on 11/11/2017.
 */
public class Storage {


    /**
     * @return
     */
    public static ArrayList<Account> getAllAccounts() {
        Account acc;
        ArrayList<Account> list = new ArrayList<Account>();
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(FILE_NAME);
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(new Account(line.substring(0,
                        line.indexOf(";")), line.substring(line.indexOf(";"), line.length()
                )));
            }
            return list;
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
    }

    /**
     *
     * @param acc
     */
    public static void addAccountToStorage(Account acc) {
        FileWriter fw;
        PrintWriter pw;

        try {
            fw = new FileWriter(FILE_NAME);
            pw = new PrintWriter(fw);

            pw.append(acc.getUsername() + ";" + acc.getPassword());
            pw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /**
     * @param book
     */
    public static void saveAccountBookToStorage(AccountsBook book) {
        FileWriter fw;
        PrintWriter pw;

        try {
            fw = new FileWriter(FILE_NAME);
            pw = new PrintWriter(fw);

            pw.write("");
            for (Account acc : book) {
                pw.append(acc.getUsername() + ";" + acc.getPassword());
            }

            pw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
