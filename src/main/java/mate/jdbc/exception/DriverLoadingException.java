package mate.jdbc.exception;

public class DriverLoadingException extends RuntimeException {
    public DriverLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
