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
            throw new RuntimeException("Can't load JDBC Driver for my SQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "1234");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/manufacturers_db", properties);
        } catch (SQLException throwables) {
            throw new RuntimeException(
                    "Can't create connection to Data Base \"manufacturer\"", throwables);
        }
    }
}
