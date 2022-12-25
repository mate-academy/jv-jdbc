package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER_JDBC = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root_user";
    private static final String USER_PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(DRIVER_JDBC);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
