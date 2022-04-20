package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vitalik88";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbCredentials = new Properties();
        dbCredentials.put("user", USERNAME);
        dbCredentials.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL, dbCredentials);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection with DB", e);
        }
    }
}
