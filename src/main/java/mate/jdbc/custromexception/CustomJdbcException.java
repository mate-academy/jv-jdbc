package mate.jdbc.custromexception;

public class CustomJdbcException extends RuntimeException {
    public CustomJdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomJdbcException(String message) {
        super(message);
    }
}
