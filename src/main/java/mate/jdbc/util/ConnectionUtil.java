package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/taxi_service_db", "root", "12345678");
        } catch (SQLException e) {
            throw new RuntimeException("Can't creat coonection to DB", e);
        }
    }
}
