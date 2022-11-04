package mate.jdbc.dao.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable e) {
        super(message, e);
    }

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(Throwable ex) {
        super(ex);
    }
}
