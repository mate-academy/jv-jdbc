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
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "vitalii");
        properties.put("password", "kdY81urt");
        String dbUrl = "jdbc:mysql://localhost:3306/taxi_service_db";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbUrl, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB by URL: " + dbUrl +
                    ", user: " + properties.get("user") +
                    ", password: " + properties.get("password"), e);
        }
        return connection;
    }
}

