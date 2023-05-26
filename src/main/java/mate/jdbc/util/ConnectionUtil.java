package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/taxi_service_db";

    static {
        try {
            Class.forName(MYSQL_DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "12345678");
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to" + CONNECTION_URL, e);
        }
    }
}
