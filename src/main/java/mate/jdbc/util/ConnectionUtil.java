package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String HIDDEN_PASSWORD = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver of MySQL.", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", HIDDEN_PASSWORD);
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service", properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection to database.", e);
        }
    }
}
