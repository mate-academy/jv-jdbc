package mate.jdbc;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable throwable) {
        super(massage, throwable);
    }
}
