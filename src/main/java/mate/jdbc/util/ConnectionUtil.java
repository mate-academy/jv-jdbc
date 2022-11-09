package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        try {
            return DriverManager.getConnection(DATABASE_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection "
                    + "to db with following url:"
                    + DATABASE_URL, e);
        }
    }
}
