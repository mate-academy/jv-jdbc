package mate.jdbc.exeption;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException() {
    }

    public DataProcessingException(String message, Exception err) {
        super(message, err);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}
