package mate.jdbc.lib.exception;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public DataProcessingException(String exceptionMessage, Throwable exception) {
        super(exceptionMessage, exception);
    }
}
