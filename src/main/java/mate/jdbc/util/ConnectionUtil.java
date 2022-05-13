package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/manufacturers";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't crating connection to JDBC driver for MySql", e);
        }
    }
}
