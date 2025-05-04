package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
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
            Properties properties = new Properties();
            properties.put("user", USERNAME);
            properties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, properties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
