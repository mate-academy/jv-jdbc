package mate.jdbc.lib;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable exception) {
        super(massage, exception);
    }
}
