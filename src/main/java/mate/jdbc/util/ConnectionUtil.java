package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String USER = "user";
    private static final String PROPERTIES_USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String PROPERTIES_PASSWORD = "M80953980689m";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL_FOR_CONNECTION = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, PROPERTIES_USER_NAME);
            dbProperties.put(PASSWORD, PROPERTIES_PASSWORD);
            return DriverManager.getConnection(URL_FOR_CONNECTION,
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection with DB", e);
        }
    }
}
