package Exceptions;

import java.util.List;

public class PasswordTooWeakException extends MyException {
    public PasswordTooWeakException(String msg) {
        super(msg);
    }

    public PasswordTooWeakException() {
        super("Password is too weak");
    }
    
    public PasswordTooWeakException(String msg, String htmlMsg) {
        super(msg, htmlMsg);
    }
    
    public PasswordTooWeakException(String msg, List<String> htmlMsg) {
        super(msg, htmlMsg);
    }
}