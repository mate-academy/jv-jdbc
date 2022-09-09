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
            throw new RuntimeException("Can't load JDBC for MySQL", e);
        }
    }

    public static Connection getConnection() {
        String path = "jdbc:mysql://localhost:3306/taxi_service_db";
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "12Marvel21");
            return DriverManager.getConnection(path, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't crate connection to DB", e);
        }
    }
}
