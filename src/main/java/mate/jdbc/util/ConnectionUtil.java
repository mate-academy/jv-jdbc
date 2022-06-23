package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "lrngsql";
    private static final String USER_PASSWORD = "1234567890";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER_NAME);
        dbProperties.put("password", USER_PASSWORD);
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB with user name: "
                    + USER_NAME, e);
        }
    }
}
