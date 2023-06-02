package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    public static final String MY_SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String USER_VALUE = "root";
    public static final String PASSWORD_VALUE = "";
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
            properties.put(USER, USER_VALUE);
            properties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(DB_PATH, properties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can't open connection to JDBC", throwable);
        }
    }
}
