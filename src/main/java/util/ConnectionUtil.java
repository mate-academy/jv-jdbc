package util;

import exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Azunen13");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/manufacturer", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create connection to DB", e);
        }
    }
}

