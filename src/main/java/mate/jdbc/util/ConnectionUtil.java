package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/taxi_service";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "pass";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL",e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user",LOGIN);
            dbProperties.put("password",PASSWORD);
            return DriverManager.getConnection(URL,dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to Db", e);
        }
    }
}
