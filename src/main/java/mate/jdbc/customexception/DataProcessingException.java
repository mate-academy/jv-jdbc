package mate.jdbc.customexception;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(Throwable ex) {
        super(ex);
    }

    public DataProcessingException(String message, Throwable ex) {
        super(message, ex);
    }
}
