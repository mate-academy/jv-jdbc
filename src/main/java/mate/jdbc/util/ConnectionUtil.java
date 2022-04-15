package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD_VALUE = "2012";
    private static final String URL_DATABASE
            = "jdbc:mysql://127.0.0.1:3306/taxi_service_db?serverTimezone=UTC";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver!" + DRIVER_NAME, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_VALUE);
            dbProperties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(URL_DATABASE, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection" + URL_DATABASE
                    + "where login: " + USER_VALUE, e);
        }
    }
}
