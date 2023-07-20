package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_STRING = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "taxi_service_db";
    private static final String LOGIN_USER = "root";
    private static final String LOGIN_PASSWORD = "111111qQ!";

    static {
        try {
            Class.forName(DRIVER_STRING);
        } catch (ClassNotFoundException error1) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", error1);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", LOGIN_USER);
            dbProperties.put("password", LOGIN_PASSWORD);
            return DriverManager.getConnection(
                    DB_CONNECTION_URL + DB_NAME, dbProperties);
        } catch (SQLException error2) {
            throw new RuntimeException("Can't create connection to DataBase", error2);
        }
    }
}
