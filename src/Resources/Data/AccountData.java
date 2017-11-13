package Resources.Data;

public class AccountData {
    public String username;
    public String password;

    public AccountData() {
    }

    public AccountData(String identifier, String password) {
        this.username = identifier;
        this.password = password;
    }
}
