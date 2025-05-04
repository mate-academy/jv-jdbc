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
            throw new RuntimeException("Can't load jdbc Driver for mysql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "Optipist");
            dbProperties.put("password", "2811vlad");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/manufacturers_db", dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't connect to db", throwables);
        }
    }
}
