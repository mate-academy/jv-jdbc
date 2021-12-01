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
            throw new RuntimeException("Cannot load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "sql333");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e1) {
            throw new RuntimeException("Cannot create connection to DB", e1);
        }
    }
}
