package Exceptions;

public class LockedAccountException extends Exception {
    public LockedAccountException(String msg) {
        super(msg);
    }
}
