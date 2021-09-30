package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(Throwable cause) {
        super(cause);
    }
}
