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
            throw new RuntimeException("Couldn't load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "test");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to db", e);
        }
    }
}
