package Util;

import Exceptions.*;
import Resources.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Authenticator {
    public static void create_account(String name, String pwd1, String pwd2) throws PasswordMismatchException, UsernameInUseException {
        if (!pwd1.equals(pwd2))
            throw new PasswordMismatchException("The passwords do not match");
        if (Storage.getAccount(name) != null)
            throw new UsernameInUseException("The username is already in use");

        Account acc = new Account(name, pwd1);
        Storage.addAccount(acc);
    }

    public static void delete_account(String name) throws UndefinedAccountException {
        int i = Storage.removeAccount(name);
        if (i==0)
            throw new UndefinedAccountException("No account was found");
    }


    public static void change_pwd(String name, String pwd1, String pwd2) throws UndefinedAccountException, PasswordMismatchException {
        if (!pwd1.equals(pwd2))
            throw new PasswordMismatchException("The passwords do not match");
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccountException("No account was found");
        acc.setPassword(pwd1);
        Storage.updateAccount(acc);
    }

    public static Account login(String name, String pwd) throws LockedAccountException, AuthenticationErrorException, UndefinedAccountException {
        Account acc = get_account(name);

        String saltedpwd = null;
        try {
            saltedpwd = CryptoUtil.encrypt(pwd + acc.getSalt());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (acc.getPassword().equals(saltedpwd)) {
            if (acc.isLocked())
                throw new LockedAccountException("This account is locked");
            acc.setLoggedIn(true);
            Storage.updateAccount(acc);
            return acc;
        } else {
            throw new AuthenticationErrorException("The password is invalid");
        }
    }

    public static void logout(Account acc) throws UndefinedAccountException {
        get_account(acc.getUsername());
        acc.setLoggedIn(false);
        Storage.updateAccount(acc);
    }

    public static Account get_account(String name) throws UndefinedAccountException {
        Account acc = Storage.getAccount(name);
        if (acc == null)
            throw new UndefinedAccountException("No account was found");
        return acc;
    }

    public static Account login(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationErrorException {
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
            Account acc2 = get_account(acc.getUsername());
            if (!acc.equals(acc2))
                throw new AuthenticationErrorException("User not authenticated");
        } catch (Exception e) {
            throw new AuthenticationErrorException("User not authenticated");
        }
        return acc;
    }
}
