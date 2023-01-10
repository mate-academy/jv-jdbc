package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_LOGIN = "root";
    private static final String DB_PASSWORD = "test123#";
    private static final String DB_CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/taxi_service_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", DB_LOGIN);
        dbProperties.put("password", DB_PASSWORD);
        try {
            return DriverManager.getConnection(DB_CONNECTION_URL,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB");
        }
    }
}
