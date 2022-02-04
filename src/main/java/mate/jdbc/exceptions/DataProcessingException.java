package mate.jdbc.exceptions;

public class DataProcessingException extends RuntimeException {

    //String message and Throwable ex
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
