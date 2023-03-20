package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String URL
            = "jdbc:mysql://localhost:3306/db_taxi?serverTimezone=Europe/Kiev";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.put("user", USERNAME);
            dbproperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, dbproperties);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create connection to DB ", throwables);
        }
    }
}
