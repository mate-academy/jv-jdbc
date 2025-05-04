package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(final String message, final Throwable ex) {
        super(message, ex);
    }
}
