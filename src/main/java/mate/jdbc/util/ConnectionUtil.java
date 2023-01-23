package mate.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Can't register JDBC driver for MySQL.", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    "root", "parol1408");
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB.", e);
        }
    }
}
