package mate.jdbc.util;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
