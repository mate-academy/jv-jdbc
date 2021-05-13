package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/manufacturers";
    private static final Properties dbProperties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
        dbProperties.put("user", "root");
        dbProperties.put("password", "12345678");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB. URL " + DB_URL, e);
        }
    }
}
