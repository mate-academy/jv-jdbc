package mate.jdbc.excpetions;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String errorMessage, SQLException e) {
        super(errorMessage, e);
    }

}
