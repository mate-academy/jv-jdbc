package mate.jdbc.exception;

public class SqlRuntimeException extends RuntimeException {
    public SqlRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
