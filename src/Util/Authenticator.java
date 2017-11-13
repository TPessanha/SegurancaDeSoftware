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

    static void delete_account(String name) {
        return;
    }

    static void change_pwd(String name, String pwd1, String pwd2) {
        return;
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
            return acc;
        } else {
            throw new AuthenticationError("The password is invalid");
        }
    }

    public static void logout(Account acc) {
        return;
    }

    static Account get_account(String name) {
        return null;
    }

    public static Account login(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
