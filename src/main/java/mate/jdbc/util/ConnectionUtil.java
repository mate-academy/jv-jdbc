package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String SQL_NAME = "root";
    private static final String SQL_PASSWORD = "nonstop81";
    private static final String SQL_CONNECTION_ADDRESS = "jdbc:mysql://127.0.0.1:3306/taxi_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", SQL_NAME);
            dbProperties.put("password", SQL_PASSWORD);
            return DriverManager.getConnection(SQL_CONNECTION_ADDRESS, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to "
                    + SQL_CONNECTION_ADDRESS, e);
        }
    }
}
