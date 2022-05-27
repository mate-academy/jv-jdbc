package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        super(message, ex);
    }
}
