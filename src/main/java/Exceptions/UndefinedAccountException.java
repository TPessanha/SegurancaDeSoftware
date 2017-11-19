package Exceptions;

public class UndefinedAccountException extends MyException {
    public UndefinedAccountException(String msg) {
        super(msg);
    }
    public UndefinedAccountException() {
        super("No account was found","Invalid userID or password");
    }
    public UndefinedAccountException(String msg, String htmlMsg) {
        super(msg, htmlMsg);
    }
}
