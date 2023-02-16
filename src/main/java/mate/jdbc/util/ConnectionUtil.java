package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "4444";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
