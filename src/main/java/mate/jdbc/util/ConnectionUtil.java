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
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "junior");
            return DriverManager.getConnection(
                    "jdbc:mysql://127.0.01:3306/library_db",dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't create JDBC connection to DB", throwable);
        }
    }
}
