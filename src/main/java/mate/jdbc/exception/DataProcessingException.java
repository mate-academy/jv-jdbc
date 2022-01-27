package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException() {
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
