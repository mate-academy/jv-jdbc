package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProgressingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProgressingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", "root");
            propertiesDb.put("password", "maks");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    propertiesDb);
        } catch (SQLException e) {
            throw new DataProgressingException("Can't create connection to DB", e);
        }
    }
}
