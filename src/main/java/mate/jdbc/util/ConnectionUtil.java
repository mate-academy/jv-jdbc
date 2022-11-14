package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String LOCAL_URL_DATABASE = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String HOST_NAME = "root";
    private static final String HOST_PASSWORD = "1234";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL",e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user",HOST_NAME);
            properties.put("password",HOST_PASSWORD);
            return DriverManager.getConnection(LOCAL_URL_DATABASE, properties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB",throwables);
        }
    }
}
