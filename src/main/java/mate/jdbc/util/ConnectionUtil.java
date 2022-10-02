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
            throw new RuntimeException("Cannot load MySQL driver", e);
        }
    }

    public static Connection getConnection() {
        Properties db = new Properties();
        db.setProperty("user", "root");
        db.setProperty("password", "8u27898Y8u27898Y");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service", db);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to DB", e);
        }
    }
}
