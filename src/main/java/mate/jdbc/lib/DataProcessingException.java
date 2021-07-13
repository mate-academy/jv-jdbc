package mate.jdbc.lib;

public class DataProcessingException extends RuntimeException{
    public DataProcessingException(String message, Throwable ex) {
        super(message,ex);
    }
}
