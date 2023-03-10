package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_KEY = "user";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD_KEY = "password";
    private static final String PASSWORD_VALUE = "123456";
    private static final String CONNECTION_URL
            = "jdbc:mysql://localhost:3306/taxi_service_database";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put(USER_KEY, USER_VALUE);
        properties.put(PASSWORD_KEY, PASSWORD_VALUE);
        try {
            return DriverManager.getConnection(CONNECTION_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to the database", e);
        }
    }
}
