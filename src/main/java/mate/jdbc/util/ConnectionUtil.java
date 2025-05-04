package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String PASSWORD = "12345";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USER_NAME = "root";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL_DB, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to db", e);
        }
    }
}
