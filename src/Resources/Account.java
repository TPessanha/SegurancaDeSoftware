package Resources;

import Util.CryptoUtil;

import java.security.SecureRandom;
import java.util.Base64;

public class Account {
    private final String username;
    private String password;
    private boolean loggedIn;
    private boolean locked;
    private Role role;
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
        this.role = Role.USER;
        this.locked = false;
    }

    public Account(String username, String password, String salt, String role, String locked, String loggedIn) {
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.loggedIn = loggedIn.equals("true");
        this.locked = locked.equals("true");
        this.role = Role.fromValue(role);
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
    public Role getRole() {
        return role;
    }

    /**
     * Sets the account role
     *
     * @param role An enum of Account.role
     */
    public void setRole(Role role) {
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

    public String getSalt() {
        return salt;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
