package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver: " + JDBC_DRIVER, e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(DB_HOST, properties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to the DB: "
                    + DB_HOST + " User: " + USER, throwables);
        }
    }
}
