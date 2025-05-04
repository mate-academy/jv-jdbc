package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VALUE = "11111111";
    private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_NAME);
            dbProperties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(JDBC_CONNECTION, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't create connection to DB", throwable);
        }
    }
}
