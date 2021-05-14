package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String LOGIN = "root";
    private static final String PASSWORD = "12345";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/manufacturers_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to download the driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties userProperties = new Properties();
            userProperties.put("user", LOGIN);
            userProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, userProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }
}

