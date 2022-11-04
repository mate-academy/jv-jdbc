package mate.jdbc.dao.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable ex) {
        super(message, ex);
    }
}
