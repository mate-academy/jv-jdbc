package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PASSWORD = "567321";
    private static final String USER_NAME = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service_db",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }

}
