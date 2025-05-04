package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String msg, Throwable e) {
        super(msg, e);
    }

    public DataProcessingException(String msg) {
        super(msg);
    }
}
