package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "taxi_service";
    private static final String PASSWORD = "jXmXIUZ@t66.cq7U";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service";
    private static final Properties dbProperties = new Properties();

    static {
        dbProperties.put("user", USERNAME);
        dbProperties.put("password", PASSWORD);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t open connection !!!", e);
        }
    }
}
