package Resources;

import Util.Storage;

import java.util.ArrayList;
import java.util.List;

public class AccountsBook {

    private List<Account> book;

    public AccountsBook() {
        book = new ArrayList<Account>();
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

    public void addAccount(Account acc) {
        book.add(acc);
    }

    public void deleteAccount(String username) {
        int i = 0;

        for (Account acc : book) {
            if (acc.getUsername().equals(username)) {
                book.remove(i);
                return;
            }
            i++;
        }
    }

    public List<Account> getAllAccounts() {
        return book;
    }
}
