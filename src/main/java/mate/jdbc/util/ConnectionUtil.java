package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL_DB_CONNECTION = "jdbc:mysql://localhost/taxi_service_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "753159";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", USER_NAME);
        connectionProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL_DB_CONNECTION, connectionProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection to DB", e);
        }
    }
}
