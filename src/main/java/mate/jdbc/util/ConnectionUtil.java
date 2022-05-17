package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "Mysql777777";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed tt load JDBC driver for MySQL " + JDBC_DRIVER, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection to db", e);
        }
    }
}
