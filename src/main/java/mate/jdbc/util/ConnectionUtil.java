package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProgressingException;

public class ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "451276";
    private static final String DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProgressingException("Can't create connection to DB", e);
        }
    }
}
