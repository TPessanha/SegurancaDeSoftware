package Util;

import Exceptions.*;
import Resources.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Authenticator {
    public static void create_account(String name, String pwd1, String pwd2) throws PasswordMismatch, UsernameInUse {
        if (!pwd1.equals(pwd2))
            throw new PasswordMismatch("The passwords do not match");
        if (Storage.getAccount(name) != null)
            throw new UsernameInUse("The username is already in use");

        Account acc = new Account(name, pwd1);
        Storage.addAccount(acc);
    }

    public static void delete_account(String name) {
        Storage.removeAccount(name);
    }

    static void change_pwd(String name, String pwd1, String pwd2) throws UndefinedAccount, PasswordMismatch {
        if (!pwd1.equals(pwd2))
            throw new PasswordMismatch("The passwords do not match");
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccount("No account was found");
        acc.setPassword(pwd1);
        Storage.updateAccount(acc);
    }

    public static Account login(String name, String pwd) throws UndefinedAccount, LockedAccount, AuthenticationError {
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccount("No account was found");

        String saltedpwd = null;
        try {
            saltedpwd = CryptoUtil.encrypt(pwd + acc.getSalt());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (acc.getPassword().equals(saltedpwd)) {
            if (acc.isLocked())
                throw new LockedAccount("This account is locked");
            acc.setLoggedIn(true);
            Storage.updateAccount(acc);
            return acc;
        } else {
            throw new AuthenticationError("The password is invalid");
        }
    }

    static void logout(Account acc) throws UndefinedAccount {
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccount("No account was found");
        acc.setLoggedIn(false);
        Storage.updateAccount(acc);
    }

    public static Account get_account(String name) throws UndefinedAccount {
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccount("No account was found");
        return acc;
    }

    static Account login(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationError {
        Account acc;
        try {
            acc = new Account(
                    req.getSession().getAttribute("USER").toString(),
                    req.getSession().getAttribute("PASS").toString(),
                    req.getSession().getAttribute("SALT").toString(),
                    req.getSession().getAttribute("ROLE").toString(),
                    req.getSession().getAttribute("LOCKED").toString(),
                    req.getSession().getAttribute("LOGGED_IN").toString()
            );
        } catch (Exception e) {
            throw new AuthenticationError("User not authenticated");
        }
        return acc;
    }
}
