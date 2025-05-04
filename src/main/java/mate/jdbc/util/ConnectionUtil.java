package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String userName = "root";
    private static final String password = "1234567890";
    private static final String taxiServiceUrl = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String className = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", userName);
            dbProperties.put("password", password);
            return DriverManager.getConnection(taxiServiceUrl,
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to DB", e);
        }
    }
}
