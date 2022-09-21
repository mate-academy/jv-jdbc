package mate.jdbc.exception;

public class ProcessException extends RuntimeException {
    public ProcessException(String message, Throwable ex) {
        super(message, ex);
    }
}
