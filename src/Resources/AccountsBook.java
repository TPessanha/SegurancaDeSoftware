package Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 11/11/2017.
 */
public class AccountsBook {

    List<Account> book = new ArrayList<Account>();

    public AccountsBook() {
    }

    public void loadAccounts() {
        book = Storage.getAllAccounts();
    }

    public Account getAccount(String username) {
        for (Account acc : book) {
            if (acc.getUsername().equals(username))
                return acc;
        }
        return null;
    }
}
