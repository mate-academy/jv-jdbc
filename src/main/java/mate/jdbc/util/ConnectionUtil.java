package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "005764";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed", e);
        }
    }
}
