package Exceptions;

public class LockedAccount extends Exception {
    public LockedAccount(String msg) {
        super(msg);
    }
}
