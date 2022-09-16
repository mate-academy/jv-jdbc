package mate.jdbc.util;

import mate.jdbc.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", ex);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "826220228");
        try {
           return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service_db", dbProperties);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create connection to DB", ex);
        }
    }
}
