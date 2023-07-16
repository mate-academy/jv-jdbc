package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load driver");
        }
    }

    private static final String USER = "root";
    private static final String PASSWORD = "as12df34";
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_service";

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t load driver");
        }
    }
}
