package mate.jdbc.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Exception e) {
        super(message, e);
    }
}
