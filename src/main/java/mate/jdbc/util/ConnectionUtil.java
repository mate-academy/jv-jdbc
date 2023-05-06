package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

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
            dbProperties.setProperty("user", "root");
            dbProperties.setProperty("password", "123456789");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to DB", e);
        }
    }
}
