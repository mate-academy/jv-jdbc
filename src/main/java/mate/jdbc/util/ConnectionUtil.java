package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final String CONNECTION_URL =
            "jdbc:mysql://localhost:3306/taxi_services?useSSL=false";

    static {
        try {
            Class.forName(JDBC_DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
