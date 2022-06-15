package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "123123");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi-service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant connect to database", e);
        }
    }
}
