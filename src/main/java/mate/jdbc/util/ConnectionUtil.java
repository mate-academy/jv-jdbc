package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PATH = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String USER = "user";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VALUE = "21024root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_VALUE);
            dbProperties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
