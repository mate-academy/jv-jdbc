package mate.jdbc.exception;

public class DataProcessingException extends RuntimeException{
    String message;
    Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        this.message = message;
        this.ex = ex;
    }
}
