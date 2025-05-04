package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "lrngsql";
    private static final String USER_PASSWORD = "1234567890";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi";
    private static final String DRIVER_URL = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL from url: "
                    + DRIVER_URL, e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER_NAME);
        dbProperties.put("password", USER_PASSWORD);
        try {
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB from url: " + CONNECTION_URL
                    + " with user name: " + USER_NAME, e);
        }
    }
}
