package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/manufacturers";
    private static final String USER = "root";
    private static final String USER_PASSWORD = "3510";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC driver for MySQL: "
                    + DB_DRIVER, e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", USER_PASSWORD);
            connection =
                    DriverManager.getConnection(DB_URL,
                            dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to DB " + DB_URL
                    + " for user: " + USER, e);
        }
        return connection;
    }
}
