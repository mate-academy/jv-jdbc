package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable te) {
        super(message, te);
    }
}
