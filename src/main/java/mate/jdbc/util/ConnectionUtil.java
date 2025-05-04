package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER_ADDRESS = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    static {
        try {
            Class.forName(DRIVER_ADDRESS);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(CONNECTION_ADDRESS, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connection to DB", e);
        }
    }
}
