package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "taxi_service";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "111111";

    static {
        try {
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver. ", e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            connection = DriverManager.getConnection(DATABASE_URL + DATABASE_NAME,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't open connection to database. ", e);
        }
        return connection;
    }
}
