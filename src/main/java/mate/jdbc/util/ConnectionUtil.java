package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL_DB, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB: "
                    + URL_DB, e);
        }
    }
}
