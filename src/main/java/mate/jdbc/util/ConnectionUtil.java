package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String DB_PASSWORD = "12345678";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_db";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC Driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER_NAME);
        dbProperties.put("password", DB_PASSWORD);
        try {
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to DB", e);
        }
    }
}
