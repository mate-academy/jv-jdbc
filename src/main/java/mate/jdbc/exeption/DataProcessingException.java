package mate.jdbc.exeption;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable throwable) {
        super(massage, throwable);
    }
}
