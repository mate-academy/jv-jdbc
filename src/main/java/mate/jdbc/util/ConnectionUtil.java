package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }

    private static String userName = "root";
    private static String password = "12345678";
    private static String DBUrl = "jdbc:mysql://localhost:3306/taxi_db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DBUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }
}
