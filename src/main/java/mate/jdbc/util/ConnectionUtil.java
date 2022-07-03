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
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbPropertires = new Properties();
            dbPropertires.put("user", "root");
            dbPropertires.put("password", "Hamam543");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service", dbPropertires);
        } catch (
                SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
