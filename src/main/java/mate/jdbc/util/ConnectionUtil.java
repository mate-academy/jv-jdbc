package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/taxi_db";

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
            dbProperties.put(USERNAME_KEY, USERNAME);
            dbProperties.put(PASSWORD_KEY, PASSWORD);
            return DriverManager.getConnection(MYSQL_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
