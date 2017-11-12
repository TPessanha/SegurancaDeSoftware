package Resources;

import Util.CryptoUtil;

/**
 * Created by tomas on 11/11/2017.
 */
public class Account {
    private String username;
    private String password;
    private boolean loggedIn;
    private boolean locked;
    private role role;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        this.locked = true;
        this.role = role.USER;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Account.role getRole() {
        return role;
    }

    public void setRole(Account.role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = CryptoUtil.encrypt(password);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean checkPassword(String password) {
        try {
            return password.equals(CryptoUtil.encrypt(password));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public enum role {
        ADMIN,
        USER
    }
}
