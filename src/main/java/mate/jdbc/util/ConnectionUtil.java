package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "7654321ewqDSA";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DatabaseURL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver " + DRIVER, e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USERNAME);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(DatabaseURL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection", e);
        }
    }
}
