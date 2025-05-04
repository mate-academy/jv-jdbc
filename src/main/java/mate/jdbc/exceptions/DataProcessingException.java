package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable issue) {
        super(message, issue);
    }
}
