package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER_NAME = "user";
    private static final String LOGIN = "root";
    private static final String USER_PASSWORD = "password";
    private static final String PASSWORD = "Madskiled1";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put(USER_NAME, LOGIN);
            properties.put(USER_PASSWORD, PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
