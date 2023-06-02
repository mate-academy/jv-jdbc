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
            throw new RuntimeException("Can't connect load driver for JDBC", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "subede14sql295my");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection to data base", e);
        }
    }
}
