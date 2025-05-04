package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "user";
    public static final String USER_NAME_ROOT = "root";
    public static final String PASSWORD = "password";
    public static final String USERS_PASSWORD = "1234";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_NAME_ROOT);
            dbProperties.put(PASSWORD, USERS_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect with DB. URL: "
                    + DB_URL + "User: " + USER_NAME_ROOT + " ", e);
        }
    }
}
