package mate.jdbc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.lib.exception.DataProcessingException;

public class ConnectionSupplier {

    private static final String DB_DRIVER_EXCEPTION = "Failed to initialize db connection driver.";
    private static final String CONNECTION_EXCEPTION = "Can't create db connection";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException(DB_DRIVER_EXCEPTION, e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "user");
        dbProperties.put("password", "1111");
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service",
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException(CONNECTION_EXCEPTION, e);
        }
    }
}
