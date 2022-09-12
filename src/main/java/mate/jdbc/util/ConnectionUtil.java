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
            throw new RuntimeException("Can't load MySQL driver.", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties connectionProperties = new Properties() {
                {
                    put("user", "root");
                    put("password", "root");
                }
            };
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service", connectionProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to DB was failed.", e);
        }
    }
}
