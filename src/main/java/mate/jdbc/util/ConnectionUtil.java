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
            throw new RuntimeException("Can't load jdbc driver classes ", e);
        }
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/taxi_service";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "passroot");
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver with url: " + url
                    + " user: " + properties.getProperty("user"), e);
        }
    }
}
