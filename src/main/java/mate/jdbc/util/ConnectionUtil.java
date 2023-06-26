package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String USER_PASSWORD = "13289812";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USERNAME);
            dbProperties.put(PASSWORD, USER_PASSWORD);
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to Database", e);
        }
    }
}
