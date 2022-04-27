package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL
            = "jdbc:mysql://localhost:3306/taxi_service?serverTimezone=UTC";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find " + DRIVER_NAME, e);
        }
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to " + URL, e);
        }
    }
}
