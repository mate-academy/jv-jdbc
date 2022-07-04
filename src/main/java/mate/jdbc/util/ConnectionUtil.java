package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "AnonymouS007";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t find JDBC Driver class", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER_NAME);
        dbProperties.put("password", USER_PASSWORD);
        try {
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get connection from url: " + CONNECTION_URL, e);
        }
    }
}
