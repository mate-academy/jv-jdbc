package mate.jdbc.exception;

public class DataException extends RuntimeException {
    public DataException(String message, Throwable ex) {
        super(message, ex);
    }
}
