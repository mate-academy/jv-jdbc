package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "mate_academy235";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/manufacturer";

    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver ", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.put("user", USER_NAME);
            dbproperties.put("password", PASSWORD);
            return DriverManager.getConnection(DATABASE_URL, dbproperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to db ", e);
        }
    }
}
