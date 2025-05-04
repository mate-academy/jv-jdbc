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
            throw new RuntimeException("Can't load Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Ilia290900RR");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/manufacture", dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't connect to DB", throwable);
        }
    }
}
