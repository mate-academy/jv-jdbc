package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String CLAZZ_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME_FOR_DB = "root";
    private static final String USER_PASSWORD_FOR_DB = "12345789";
    private static final String WAY_TO_SCHEMA = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(CLAZZ_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME_FOR_DB);
            dbProperties.put("password", USER_PASSWORD_FOR_DB);
            return DriverManager.getConnection(WAY_TO_SCHEMA, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't creat connection to DB", e);
        }
    }
}
