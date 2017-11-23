package Exceptions;

public class UsernameDoesNotMeetRequirementsException extends MyException {
    public UsernameDoesNotMeetRequirementsException(String msg) {
        super(msg);
    }

    public UsernameDoesNotMeetRequirementsException() {
        super("Username does not meet requirements");
    }

    public UsernameDoesNotMeetRequirementsException(String msg, String htmlMsg) {
        super(msg, htmlMsg);
    }
}
