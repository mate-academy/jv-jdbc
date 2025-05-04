package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String exceptionMessage, Throwable throwable) {
        super(exceptionMessage, throwable);
    }
}
