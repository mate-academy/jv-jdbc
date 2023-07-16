package mate.jdbc.dao.exception;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String message, SQLException e) {
    }
}
