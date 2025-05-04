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
            throw new DataProcessingException("Can't load jdbc Driver for MySQL", e);
        }
    }
    
    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", "root");
            propertiesDb.put("password", "3215987");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db",
                    propertiesDb);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
