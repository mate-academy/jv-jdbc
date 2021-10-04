package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {
    DataProcessingException(String message, Throwable ex) {
        super(message, ex);
    }
}
