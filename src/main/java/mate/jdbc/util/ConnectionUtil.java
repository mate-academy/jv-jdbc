package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "your user";
    private static final String PASSWORD = "your password";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();

        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to DB", e);
        }
    }
}
