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
    private static final String EXCEPTION_LOAD_DRIVER = "Can't load driver!";
    private static final String EXCEPTION_CREATE_CONNECTION_PART_1 = "Can't create connection";
    private static final String EXCEPTION_CREATE_CONNECTION_PART_2 = "where login: ";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(EXCEPTION_LOAD_DRIVER + DRIVER_NAME, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_VALUE);
            dbProperties.put(PASSWORD, PASSWORD_VALUE);
            return DriverManager.getConnection(URL_DATABASE, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException(EXCEPTION_CREATE_CONNECTION_PART_1 + URL_DATABASE
                    + EXCEPTION_CREATE_CONNECTION_PART_2 + USER_VALUE, e);
        }
    }
}
