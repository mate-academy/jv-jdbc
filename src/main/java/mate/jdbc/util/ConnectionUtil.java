package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.ConnectionException;

public class ConnectionUtil {
    private static final String DRIVER_LOCATION = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "password";

    static {
        try {
            Class.forName(DRIVER_LOCATION);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_PROPERTY, USER_NAME);
            dbProperties.put(PASSWORD_PROPERTY, USER_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
