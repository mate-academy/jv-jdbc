package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_LOCAL_HOST = "jdbc:mysql://localhost:3306/library_db";
    private static final String DATA_BASE_LOGIN = "root";
    private static final String DATA_BASE_PASSWORD = "123123";

    static {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DATA_BASE_LOGIN);
            dbProperties.put("password", DATA_BASE_PASSWORD);
            return DriverManager.getConnection(MYSQL_LOCAL_HOST, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to db", throwables);
        }
    }
}
