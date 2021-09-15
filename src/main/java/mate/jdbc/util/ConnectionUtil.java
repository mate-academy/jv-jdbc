package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String dbLink = "jdbc:mysql://localhost:3306/library_db";
    private static final String driverLink = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(driverLink);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for my SQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1234");
            return DriverManager.getConnection(dbLink,
                    dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
