package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_JDBC = "com.mysql.cj.jdbc.Driver";
    private static final String USER_PARAMETER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_PASSWORD = "linkweed99Uteam";
    private static final String MYSQL_CONNECTION = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER_JDBC);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_PARAMETER, USER_NAME);
            dbProperties.put(PASSWORD_PARAMETER, USER_PASSWORD);
            return DriverManager.getConnection(MYSQL_CONNECTION, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't crate connection to DB", throwables);
        }
    }
}
