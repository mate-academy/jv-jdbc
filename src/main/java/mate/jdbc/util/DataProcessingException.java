package mate.jdbc.util;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
