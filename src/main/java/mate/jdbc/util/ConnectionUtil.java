package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String TAXI_SERVICE_CONNECTION_URL =
            "jdbc:mysql://localhost:3306/taxi_service_db";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "qwer";

    static {
        try {
            Class.forName(JDBC_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t download JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(TAXI_SERVICE_CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB", e);
        }
    }
}
