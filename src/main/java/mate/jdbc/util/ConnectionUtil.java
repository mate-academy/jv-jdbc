package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_KEY = "user";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD_KEY = "password";
    private static final String PASSWORD_VALUE = "091088";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_KEY, USER_VALUE);
            dbProperties.put(PASSWORD_KEY, PASSWORD_VALUE);
            return DriverManager.getConnection(URL_DB, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
