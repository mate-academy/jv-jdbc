package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "5002";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Can't establish the connection to DB", e);
        }
    }
}
