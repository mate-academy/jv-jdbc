package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME_FOR_DB = "root";
    private static final String USER_PASSWORD_FOR_DB = "12345789";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME_FOR_DB);
            dbProperties.put("password", USER_PASSWORD_FOR_DB);
            return DriverManager.getConnection(DATABASE_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't creat connection to DB", e);
        }
    }
}
