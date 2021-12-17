package mate.jdbc.dao;

public class DataProcessingException extends RuntimeException {
    private Throwable exception;

    public DataProcessingException(String message, Throwable exception) {
        super(message);
        this.exception = exception;
    }
}
