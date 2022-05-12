package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/taxi_app_db";
    private static final String USER = "root";
    private static final String PASSWORD = "gnrPswrd";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(CONNECTION_ADDRESS,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed", e);
        }
    }
}
