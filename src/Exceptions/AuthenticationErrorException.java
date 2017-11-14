package Exceptions;

public class AuthenticationErrorException extends Exception {
    public AuthenticationErrorException(String msg) {
        super(msg);
    }
}
