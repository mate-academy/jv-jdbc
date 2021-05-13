package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/manufacturers_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't download driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties userProperties = new Properties();
            userProperties.put("user", "root");
            userProperties.put("password", "12345678");
            return DriverManager.getConnection(URL, userProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
