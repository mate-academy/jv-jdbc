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
            throw new RuntimeException("Cant load JDBC driver mysql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "1289");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", properties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection to DB", e);
        }
    }
}
