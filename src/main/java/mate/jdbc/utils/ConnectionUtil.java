package mate.jdbc.utils;

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
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "35amipar");
        try {
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi_manufactures",
                            properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connection to DB!", e);
        }
    }
}
