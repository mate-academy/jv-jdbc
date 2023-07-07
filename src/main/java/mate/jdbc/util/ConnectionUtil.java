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
            throw new RuntimeException("Can't load jdbc driver for my sql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Leshka712380");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/libray_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connection on db", e);
        }
    }
}
