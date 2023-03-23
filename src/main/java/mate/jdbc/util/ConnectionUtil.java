package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER_DB_PATH = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String PASSWORD = "12345asd";
    private static final String USER = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(SERVER_DB_PATH, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database",e);
        }
    }
}
