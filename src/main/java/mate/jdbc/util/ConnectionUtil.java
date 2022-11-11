package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_PARAMETER = "user";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final String ADDRESS = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put(USER_PARAMETER, USER_NAME);
        dbProperties.put(PASSWORD_PARAMETER, USER_PASSWORD);
        try {
            return DriverManager.getConnection(ADDRESS,
                    dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
