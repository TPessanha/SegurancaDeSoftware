package Resources;

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

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    boolean checkPassword(String pwd) {
        //TODO: finish this with encrypt
        return password.equals(pwd);
    }

    public enum role {
        ADMIN,
        USER
    }
}
