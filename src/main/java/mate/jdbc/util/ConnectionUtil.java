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
            throw new RuntimeException("Cannot load mysql driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "69Irolir*");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", properties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load mysql driver", e);
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot connect to DB", throwables);
        }
    }
}
