package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_LINK = "jdbc:mysql://localhost:3306/library_db";
    private static final String DRIVER_LINK = "com.mysql.cj.jdbc.Driver";
    private static final String NAME = "root";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName(DRIVER_LINK);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for my SQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", NAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_LINK,
                    dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
