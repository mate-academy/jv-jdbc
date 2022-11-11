package mate.jdbc.lib.dao.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable exception) {
        super(message, exception);
    }

    public DataProcessingException(String message) {
        super(message);
    }

}
