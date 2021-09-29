package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String GET_PASSWORD = "Merlin18111991";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't found the driver");
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_NAME);
            dbProperties.put(PASSWORD, GET_PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to the Data Base");
        }
    }
}
