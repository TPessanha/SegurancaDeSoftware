package Util;

import Resources.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Authenticator {
    static void create_account(String name, String pwd1, String pwd2) {
        if (!pwd1.equals(pwd2))
            return;
        if (Storage.getAccount(name) != null)
            return;

    }

    static void delete_account(String name) {
        return;
    }

    static void change_pwd(String name, String pwd1, String pwd2) {
        return;
    }

    static Account login(String name, String pwd) {
        Account acc = Storage.getAccount(name);
        if (acc != null) {
            try {
                if (acc.getPassword().equals(CryptoUtil.encrypt(pwd + acc.getSalt()))) {
                    acc.setLoggedIn(true);
                    return acc;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    static void logout(Account acc) {
        return;
    }

    static Account get_account(String name) {
        return null;
    }

    static Account login(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
