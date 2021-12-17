package mate.jdbc.dao.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable e) {
        super(message, e);
    }
}
