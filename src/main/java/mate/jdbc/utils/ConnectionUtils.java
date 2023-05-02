package mate.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtils {
    private static final String DB_CONNECTION_EXCEPTION = "Can`t connect to db";
    private static final String DB_LOAD_DRIVER_EXCEPTION = "Can`t load MYSQL driver for JDBC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException(DB_LOAD_DRIVER_EXCEPTION, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "rootroot");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException(DB_CONNECTION_EXCEPTION, e);
        }
    }
}
