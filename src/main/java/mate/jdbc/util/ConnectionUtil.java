package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String LIBRARY_DB_URL = "jdbc:mysql://@localhost:3306/library_db";

    static {
        try {
            Class.forName(MYSQL_DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't get JDBC driver for MySQL!", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, "root");
            dbProperties.put(PASSWORD, "19cience19");
            return DriverManager.getConnection(LIBRARY_DB_URL, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get connection!", throwables);
        }
    }
}
