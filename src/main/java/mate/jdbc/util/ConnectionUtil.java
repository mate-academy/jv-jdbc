package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "0258";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PATH_TO_DB = "jdbc:mysql://localhost:3306/manufacturers_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(PATH_TO_DB, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to db", throwables);
        }
    }
}
