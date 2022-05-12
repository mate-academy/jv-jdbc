package mate.jdbc.custom;

public class Exception extends RuntimeException {
    public Exception(String message, Throwable e) {
        super(message, e);
    }
}
