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
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        String connectionUrl = "jdbc:mysql://localhost:3306/taxi_db";
        dbProperties.put("user", "root");
        dbProperties.put("password", "2460");
        try {
            return DriverManager.getConnection(connectionUrl, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can`t create connections to manufacturers", throwable);
        }
    }
}
