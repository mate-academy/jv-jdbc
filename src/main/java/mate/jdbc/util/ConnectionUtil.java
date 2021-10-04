package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1703";
    public static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(URL, dbProperties);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create connection to DB", throwables);
        }
    }
}
