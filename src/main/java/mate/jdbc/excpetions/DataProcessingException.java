package mate.jdbc.excpetions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

}
