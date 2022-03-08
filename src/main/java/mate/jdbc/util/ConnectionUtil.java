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
            throw new RuntimeException("Can`t load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Annamysko4202359");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB", e);
        }
    }
}
