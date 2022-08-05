package mate.jdbc.util;

import mate.jdbc.models.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "taxiadmin");
        dbProperties.put("password", "12345678");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to database", e);
        }
    }
}
