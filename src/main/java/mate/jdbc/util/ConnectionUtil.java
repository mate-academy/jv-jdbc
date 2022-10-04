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
            throw new RuntimeException("Can't load jdbc driver for MYSQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "mate");
            dbProperties.put("password", "Bghj5MjyeFgFr3");
            return DriverManager.getConnection("jdbc:mysql://192.168.0.8:3306/taxi_services_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connect to DB", e);
        }
    }
}
