package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "taxiadmin");
        dbProperties.put("password", "12345678");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to database", e);
        }
    }
}
