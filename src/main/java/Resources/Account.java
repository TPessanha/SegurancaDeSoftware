package Resources;

import Util.CryptoUtil;

import java.util.ArrayList;
import java.util.List;

import static Util.Constants.SALT_STRENGHT;

public class Account {
    private final String username;
    private String password;
    private boolean loggedIn;
    private boolean locked;
    private Role role;
    private List<String> capabilities;

    /**
     * Creates an account
     *
     * @param username The user identifier, must be unique
     * @param password The user password in clear text
     */
    public Account(String username, String password) {
        this.username = username;
        try {
            this.password = CryptoUtil.bCryptEncrypt(password, generateSalt());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        this.loggedIn = false;
        this.role = Role.USER;
        this.locked = false;
        this.capabilities = new ArrayList<>();
    }
    
    private String generateSalt() {
        return CryptoUtil.genSalt(SALT_STRENGHT);
    }
    
    public Account(String username, String password, String role, String locked, String loggedIn) {
        this.username = username.toLowerCase();
        this.password = password;
        this.loggedIn = loggedIn.equals("true");
        this.locked = locked.equals("true");
        this.role = Role.fromValue(role);
        this.capabilities = new ArrayList<>();
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
     * Sets the account login status
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
        try {
            this.password = CryptoUtil.bCryptEncrypt(password, generateSalt());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Account account = (Account) o;
        
        if (loggedIn != account.loggedIn) return false;
        if (locked != account.locked) return false;
        if (!username.equals(account.username)) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        return role == account.role;
    }
}
