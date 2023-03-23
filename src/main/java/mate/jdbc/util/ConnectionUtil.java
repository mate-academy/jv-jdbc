package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", exception);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "root");
        try {
            return DriverManager.getConnection("jdbc:mysql://"
                    + "127.0.0.1:3306/db_manufacturers", dbProperties);
        } catch (SQLException exception) {
            throw new RuntimeException("Can't establish connection to DB", exception);
        }
    }
}
