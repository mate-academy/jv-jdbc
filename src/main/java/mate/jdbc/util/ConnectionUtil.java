package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProcessingException;

public class ConnectionUtil {
    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final Properties properties;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "159y159y159y";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't create connection to db", e);
        }
        properties = new Properties();
        properties.put("user", USER_NAME);
        properties.put("password", PASSWORD);
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection to db", e);
        }
    }
}
