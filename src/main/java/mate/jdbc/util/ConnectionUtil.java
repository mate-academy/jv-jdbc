package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String REFERENCE_DB = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load a MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", USERNAME);
        properties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(REFERENCE_DB, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to Taxi Service DB", e);
        }
    }
}
