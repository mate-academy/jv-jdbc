package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String message, Throwable ex) {
        throw new RuntimeException(message, ex);
    }
}
