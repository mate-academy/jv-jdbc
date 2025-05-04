package mate.jdbc.db;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable exception) {
        super(message, exception);
    }
}
