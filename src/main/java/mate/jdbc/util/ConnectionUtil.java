package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PATH = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String DB_LOGIN = "root";
    private static final String DB_PASSWORD = "12345678";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_LOGIN);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB: " + DB_PATH, e);
        }
    }
}
