package mate.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_FIELD = "user";
    private static final String PASSWORD_FIELD = "password";
    private static final String USER_DATA = "root";
    private static final String PASSWORD_DATA = "root";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver fo MySQL ", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put(USER_FIELD, USER_DATA);
        properties.put(PASSWORD_FIELD, PASSWORD_DATA);
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    properties);
        } catch (SQLException e) {
            throw new RuntimeException("can`t create connection to database", e);
        }
    }
}
