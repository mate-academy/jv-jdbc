package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionsUtil {
    public static final String DRIVER_PACKAGE = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(DRIVER_PACKAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver ", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager
                    .getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }
}
