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
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "mysql");
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taxi_service",
                    properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to MySql", e);
        }
    }
}
