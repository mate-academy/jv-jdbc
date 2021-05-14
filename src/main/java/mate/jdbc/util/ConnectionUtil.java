package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "library_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PASSWORD);
            return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/"
                            + DATABASE, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't creat connection to DB", e);
        }
    }
}
