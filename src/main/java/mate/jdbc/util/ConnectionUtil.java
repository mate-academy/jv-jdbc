package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1234root";
    private static final String URL_DB =
            "jdbc:mysql://localhost:3306/taxi_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL_DB, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB: " + URL_DB, e);
        }
    }
}
