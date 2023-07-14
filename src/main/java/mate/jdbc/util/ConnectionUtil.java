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
            throw new RuntimeException("Can't load JDBC for my SQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "7626dfcver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_sevice_db", dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to db", throwables);
        }
    }
}
