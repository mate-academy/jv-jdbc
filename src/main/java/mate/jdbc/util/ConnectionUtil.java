package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exeptions.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", ex);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "9616");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service_db",
                    dbProperties);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create connection to DB", ex);
        }
    }
}
