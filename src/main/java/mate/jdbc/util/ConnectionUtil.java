package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String URL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_TAXI_DB =
            "jdbc:mysql://localhost:3306/taxi_base?serverTimezone=UTC";

    static {
        try {
            Class.forName(URL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver mySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL_TAXI_DB,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
