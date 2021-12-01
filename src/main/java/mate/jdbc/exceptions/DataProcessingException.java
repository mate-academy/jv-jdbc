package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
