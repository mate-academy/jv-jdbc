package mate.jdbc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.lib.exception.DataProcessingException;

public class ConnectionSupplier {
    private static final String DB_DRIVER_EXCEPTION = "Failed to initialize db connection driver.";
    private static final String CONNECTION_EXCEPTION = "Can't create db connection";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String PASSWORD = "1111";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException(DB_DRIVER_EXCEPTION, e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put(USER, USER);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(
                    CONNECTION_STRING,
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException(CONNECTION_EXCEPTION, e);
        }
    }
}
