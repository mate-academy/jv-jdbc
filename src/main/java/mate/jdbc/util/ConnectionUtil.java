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
            throw new RuntimeException("Can't lunch driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "123456");
            return DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant connection to DB", e);
        }
    }
}
