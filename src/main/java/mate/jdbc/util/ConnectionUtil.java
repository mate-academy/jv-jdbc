package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exeption.DataProcessingException;

public class ConnectionUtil {
    private static final String PASSWORD = "12345678";
    private static final String USER = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("can't load JDBCDrivers", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("can't create connection", e);
        }
    }
}
