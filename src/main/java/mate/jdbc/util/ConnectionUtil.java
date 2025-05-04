package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    public static final String MY_SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";

    static {
        try {
            Class.forName(MY_SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            initializeUser(properties);
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't open connection to JDBC", throwable);
        }
    }

    private static void initializeUser(Properties properties) {
        properties.put("user", "root");
        properties.put("password", "");
    }
}
