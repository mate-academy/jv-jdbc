package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL" + DRIVER_NAME, e);
        }

    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_LOGIN);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB" + CONNECTION_URL, e);
        }
    }
}
