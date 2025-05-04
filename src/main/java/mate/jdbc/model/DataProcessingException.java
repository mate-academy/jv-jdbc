package mate.jdbc.model;

public class DataProcessingException extends RuntimeException {
    private Throwable ex;
    private String message;

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
