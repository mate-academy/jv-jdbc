package mate.jdbc.lib.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable e) {

        super(massage);
    }
}
