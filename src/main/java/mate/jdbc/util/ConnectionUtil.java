package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DATABASE_NAME = "taxi_service_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + DATABASE_NAME, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to db: " + DATABASE_NAME, e);
        }
    }
}
