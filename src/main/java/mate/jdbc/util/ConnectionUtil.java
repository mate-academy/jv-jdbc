package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String MYSQL_DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    public static final String USER_NAME = "root";
    public static final String USER_PASSWORD = "12345678";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/manufacturer";

    static {
        try {
            Class.forName(MYSQL_DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't create connection to DB", throwable);
        }
    }
}
