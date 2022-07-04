package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Paskal19950108!";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for my SQL", e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_USER);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant` create connection to DB", e);
        }
    }
}
