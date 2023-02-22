package mate.jdbc.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable ex) {
        super(message, ex);
    }
}
