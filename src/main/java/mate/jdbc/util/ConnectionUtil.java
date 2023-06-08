package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PATH = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(
                    DB_PATH, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to DB", e);
        }
    }
}
