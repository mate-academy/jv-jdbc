package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PASSWORD = "11111111";
    private static final String USER = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL.", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesOfDb = new Properties();
            propertiesOfDb.put("user", USER);
            propertiesOfDb.put("password", PASSWORD);
            return DriverManager.getConnection(URL, propertiesOfDb);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t make connection", e);
        }
    }
}
