package jdbc.hw.dao;

public class DataProcessingException extends RuntimeException {
    String message;
    Throwable error;

    public DataProcessingException(String message, Throwable error) {
        super(message, error);
    }
}
