package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Exception exception) {
        throw new RuntimeException(message, exception);
    }
}
