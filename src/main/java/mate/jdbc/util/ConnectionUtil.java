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
            throw new RuntimeException("Can not load JDBC driver for MYSQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Satanman666");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB", e);
        }
    }
}
