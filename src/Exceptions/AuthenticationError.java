package Exceptions;

public class AuthenticationError extends Exception {
    public AuthenticationError(String msg) {
        super(msg);
    }
}
