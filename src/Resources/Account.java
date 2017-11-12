package Resources;

import Util.CryptoUtil;

import java.security.SecureRandom;
import java.util.Base64;

public class Account {
    private String username;
    private String password;
    private boolean loggedIn;
    private boolean locked;
    private role role;
    private String salt;

    /**
     * Creates an account
     *
     * @param username The user idenfifier, must be unique
     * @param password The user password in clear text
     */
    public Account(String username, String password) {
        this.username = username;
        this.salt = generateSalt();
        try {
            this.password = CryptoUtil.encrypt(password + this.salt);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        this.loggedIn = false;
        this.locked = true;
        this.role = role.USER;
    }

    public Account(String username, String password, String salt, String role, String locked, String loggedIn) {
        this.username = username;
        this.salt = salt;
        try {
            this.password = CryptoUtil.encrypt(password + salt);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        if (loggedIn.equals("true"))
            this.loggedIn = true;
        else
            this.loggedIn = false;

        if (locked.equals("true"))
            this.locked = true;
        else
            this.locked = false;
        if (role.equals("ADMIN"))
            this.role = this.role.ADMIN;
        else
            this.role = this.role.USER;
    }

    /**
     * Checks if an account is logged in
     *
     * @return TRUE if logged in FALSE otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the account loggin status
     *
     * @param loggedIn Set TRUE for logged in, set FALSE for logged out
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Checks if an account is locked
     *
     * @return TRUE if locked in FALSE otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the account locked status
     *
     * @param locked Set TRUE for locked, set FALSE for unlocked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Gets the account role
     *
     * @return The role of the account
     */
    public Account.role getRole() {
        return role;
    }

    /**
     * Sets the account role
     *
     * @param role An enum of Account.role
     */
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

        this.salt = generateSalt();
        try {

            this.password = CryptoUtil.encrypt(password + this.salt);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean checkPassword(String password) {
        try {
            return password.equals(CryptoUtil.encrypt(password + this.salt));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public String getSalt() {
        return salt;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String salt = encoder.encodeToString(bytes);
        return salt;
    }

    public enum role {
        ADMIN, USER
    }

}
