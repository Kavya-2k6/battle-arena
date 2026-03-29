package exceptions;

public class InvalidActionException extends Exception {

    public InvalidActionException(String message) {
        super(message);
    }
    public InvalidActionException(String message, Throwable cause) {
        super(message, cause);
    }
}