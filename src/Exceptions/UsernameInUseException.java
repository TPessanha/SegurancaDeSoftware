package Exceptions;

/**
 * Created by tomas on 13/11/2017.
 */
public class UsernameInUseException extends Exception {
    public UsernameInUseException(String msg) {
        super(msg);
    }
}
