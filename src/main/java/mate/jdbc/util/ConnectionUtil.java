package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/taxi-service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0602";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("can't load driver: " + DRIVER, e);
        }
    }

    public static Connection getConnect() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL_CONNECTION, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("can't connect to db ", e);
        }
    }
}
