package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VALUE = "Usenchenko19";
    private static final String DB_CONNECTION_LINK = "jdbc:mysql://localhost:3307/taxi_db";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_NAME);
            dbProperties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(DB_CONNECTION_LINK, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
