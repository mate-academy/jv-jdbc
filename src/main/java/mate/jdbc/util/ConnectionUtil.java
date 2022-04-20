package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "vitalik88");
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/manufacturers_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB ", e);
        }

    }
}
