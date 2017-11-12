package Resources;

/**
 * Created by tomas on 11/11/2017.
 */
public class Account {
    String username;
    String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String pwd) {
        //TODO: finish this with encrypt
        return password.equals(pwd);
    }
}
