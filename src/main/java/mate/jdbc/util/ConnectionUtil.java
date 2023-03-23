package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String USER_KEY = "user";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD_KEY = "password";
    private static final String PASSWORD_VALUE = "1234";

    static {
        try {
            Class.forName(JDBC_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_KEY, USER_VALUE);
            dbProperties.put(PASSWORD_KEY, PASSWORD_VALUE);
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
