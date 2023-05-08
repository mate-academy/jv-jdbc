package mate.jdbc.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String message, Throwable exception) {
        super(message);
    }
}
