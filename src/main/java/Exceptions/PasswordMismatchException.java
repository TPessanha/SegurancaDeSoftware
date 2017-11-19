package Exceptions;

public class PasswordMismatchException extends MyException {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
    public PasswordMismatchException() {
        super("The passwords do not match");
    }
    
    public PasswordMismatchException(String msg, String htmlMsg) {
        super(msg, htmlMsg);
    }
}
