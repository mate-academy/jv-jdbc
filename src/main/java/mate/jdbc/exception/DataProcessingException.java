package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errorMessage) {
        super(errorMessage);
    }

    public DataProcessingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
