package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "0654981Ad";
    private static final String CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/homework";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL ", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(CONNECTION_ADDRESS, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB", e);
        }
    }
}
