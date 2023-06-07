package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.DataProcessingException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USERNAME);
            properties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(URL, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to db", e);
        }
    }
}
