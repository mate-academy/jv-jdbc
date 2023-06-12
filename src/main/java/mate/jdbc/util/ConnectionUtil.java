package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProcessingException;

public class ConnectionUtil {
    public static final String MY_SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MY_SQL_URL = "jdbc:mysql://127.0.0.1:3306/taxi_service_db";
    public static final String MY_SQL_USER = "root";
    public static final String MY_SQL_PASSWORD = "12345";

    static {
        try {
            Class.forName(MY_SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not connect JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", MY_SQL_USER);
            properties.put("password", MY_SQL_PASSWORD);
            return DriverManager.getConnection(MY_SQL_URL, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get connection to database", e);
        }
    }
}
