package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String jdbcDriverPath = "com.mysql.cj.jdbc.Driver";
    private static final String taxiServiceTablePath = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String userName = "root";
    private static final String userPassword = "Zombie13";

    static {
        try {
            Class.forName(jdbcDriverPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", userName);
            dbProperties.put("password", userPassword);
            return DriverManager
                    .getConnection(taxiServiceTablePath, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection to JDBC", e);
        }
    }
}
