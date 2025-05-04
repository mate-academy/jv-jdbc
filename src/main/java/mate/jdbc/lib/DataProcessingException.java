package mate.jdbc.lib;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
}
