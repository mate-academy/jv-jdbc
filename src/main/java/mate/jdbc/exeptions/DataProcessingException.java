package mate.jdbc.exeptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable e) {
        super(message, e);
    }
}
