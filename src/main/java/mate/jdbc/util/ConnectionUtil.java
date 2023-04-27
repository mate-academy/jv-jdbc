package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yamysql";
    private static final String CONNECTION_PATH = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(CONNECTION_PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can’t create connection to DB", e);
        }
    }
}
