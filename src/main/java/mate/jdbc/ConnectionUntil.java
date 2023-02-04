package mate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUntil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperty = new Properties();
            dbProperty.put("user", "root");
            dbProperty.put("password", "1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    dbProperty);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection", throwables);
        }
    }
}
