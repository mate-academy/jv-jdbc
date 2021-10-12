package mate.jdbc.dao;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        this.message = message;
        this.ex = ex;
    }

    public DataProcessingException(String message, SQLException ex) {
        this.message = message;
        this.ex = ex;
    }

}
