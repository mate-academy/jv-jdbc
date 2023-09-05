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
            throw new RuntimeException("Cant load JDBC driver for MySQL", e);
        }

    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "N55sseries");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturer_db",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection with DB", e);
        }
    }
}
