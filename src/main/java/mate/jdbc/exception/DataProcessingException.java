package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
