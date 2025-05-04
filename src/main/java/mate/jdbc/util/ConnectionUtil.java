package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Marvel_dc1";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load Driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to DB", e);
        }
    }
}
