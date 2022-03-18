package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/init_db";
    private static final String USER = "root";
    private static final String PASSWORD = "HardPasSworD";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load Driver for MySQL", e);
        }
    }
    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(DB_CONNECTION_ADDRESS, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection!!", e);
        }
    }
}
