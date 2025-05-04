package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PASSWORD = "q1w2e3r4";
    private static final String URL = "jdbc:mysql://localhost:3306/manufacturer_db";
    private static final String USERNAME = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't create connection to DB", throwable);
        }
    }
}
