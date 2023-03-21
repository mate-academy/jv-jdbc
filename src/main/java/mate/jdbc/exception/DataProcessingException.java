package mate.jdbc.exception;

public class DataProcessingException extends Throwable {
    public DataProcessingException (String message, Throwable ex) {
        super(message, ex);
    }
}
