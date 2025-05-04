package mate.jdbc.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message, Throwable exception) {
        super(message, exception);
    }
}
