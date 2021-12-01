package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "mateacademy";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_USER);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB", e);
        }
    }
}
