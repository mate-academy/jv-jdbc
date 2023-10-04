package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            String jdbcUrl = "jdbc:mysql://localhost:3306/taxi_service_db";
            String user = "root";
            String password = "antonio";
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to DB", e);
        }
    }
}
