package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "root";
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL_CONNECTION = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver for sql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER_NAME);
            properties.put("password", USER_PASSWORD);
            return DriverManager
                    .getConnection(JDBC_URL_CONNECTION, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to db", e);
        }
    }
}
