package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String USER_LOGIN = "root";
    private static final String PASSWORD = "password";
    private static final String USER_PASSWORD = "sanya03";
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/taxi_service_db?serverTimezone=Europe/Kiev";

    static {
        try {
            Class.forName(DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_LOGIN);
            dbProperties.put(PASSWORD, USER_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
