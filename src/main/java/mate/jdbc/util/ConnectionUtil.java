package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "12345678";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/manufacturers_db";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver for my SQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER_NAME);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL_DB, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create Connection to DB", e);
        }
    }
}
