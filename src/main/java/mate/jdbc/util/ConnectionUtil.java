package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String DB_NAME = "taxi_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DRIVER_PACKAGE = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_PACKAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}

