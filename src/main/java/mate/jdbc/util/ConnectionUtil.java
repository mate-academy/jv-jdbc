package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "mysql";
    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/taxi_service";

    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", USER_NAME);
        properties.put("password", USER_PASSWORD);
        try {
            return DriverManager.getConnection(CONNECTION_URL,
                    properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to MySql", e);
        }
    }
}
