package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    private ConnectionUtils() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", System.getenv("db_user"));
            properties.setProperty("password", System.getenv("db_password"));
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service_db",
                    properties
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot establish new connection", e);
        }
    }
}
