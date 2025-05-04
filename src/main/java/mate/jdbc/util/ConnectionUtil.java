package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD_VALUE = "Lunchik_22";

    static {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", PASSWORD_VALUE);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't create connection to DB", throwable);
        }
    }
}
