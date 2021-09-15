package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable e) {
        throw new RuntimeException(massage, e);
    }
}
