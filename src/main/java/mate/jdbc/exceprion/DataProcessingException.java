package mate.jdbc.exceprion;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable e) {
        super(massage, e);
    }
}
