package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PATH = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver class " + DRIVER_CLASS_NAME, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("username", DB_USERNAME);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB " + DB_PATH, e);
        }
    }
}
