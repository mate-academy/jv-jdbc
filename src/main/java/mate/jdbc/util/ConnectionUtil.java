package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String LOGIN = "ROOT";
    private static final String PASSWORD = "1111";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load MYSQL JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", LOGIN);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL,
                    dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create DB connection", throwables);
        }
    }
}
