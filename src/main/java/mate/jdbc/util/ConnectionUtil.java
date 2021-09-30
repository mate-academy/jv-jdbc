package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String THE_USER = "root";
    private static final String PASSWORD = "password";
    private static final String THE_PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, THE_USER);
            dbProperties.put(PASSWORD, THE_PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't crate connection to DB for user: " + USER
                    + " and URL: "
                    + URL, e);
        }
    }
}
