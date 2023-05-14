package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String TAXI_SERVICE_CONNECTION_URL =
            "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String DB_USER_NAME = "root";
    private static final String DB_USER_PASSWORD = "JDBC1901MySQL";

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_USER_NAME);
            dbProperties.put("password", DB_USER_PASSWORD);
            return DriverManager.getConnection(TAXI_SERVICE_CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB: "
                    + TAXI_SERVICE_CONNECTION_URL, e);
        }
    }
}
