package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3307/taxi_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "3900";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver for MySQL, " + e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USERNAME);
            properties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to MySQL, " + e);
        }
    }
}
