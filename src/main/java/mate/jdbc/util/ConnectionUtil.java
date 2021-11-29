package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "testUser";
    private static final String PASSWORD = "12345678";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mysql.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", USER_NAME);
        properties.put("password", PASSWORD);
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taxi_db", properties);
    }
}
