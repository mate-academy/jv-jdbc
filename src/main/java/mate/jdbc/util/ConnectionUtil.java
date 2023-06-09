package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "22963558";
    private static final String DATABASE_PATH = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mySQL with url: "
                    + JDBC_DRIVER, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(DATABASE_PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB: "
                    + DATABASE_PATH + " with username: " + USER_NAME, e);
        }
    }
}
