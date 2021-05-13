package mate.jdbc.exception;

public class ProcessingException extends RuntimeException {
    public ProcessingException(String message, Throwable exception) {
        super(message, exception);
    }
}
