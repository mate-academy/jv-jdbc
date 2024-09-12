package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mysql.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", "testUser");
        properties.put("password", "12345678");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taxi_db", properties);
    }
}
