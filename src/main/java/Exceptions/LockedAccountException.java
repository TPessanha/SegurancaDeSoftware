package Exceptions;

public class LockedAccountException extends MyException {
    public LockedAccountException(String msg) {
        super(msg);
    }
    public LockedAccountException() {
        super("This account is locked","We recorded too many login attempts for this account, please wait and try again");
    }
    
    public LockedAccountException(String msg, String htmlMsg) {
        super(msg, htmlMsg);
    }
}
