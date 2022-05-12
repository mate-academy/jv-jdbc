package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.put("user", "mate_student");
            databaseProperties.put("password", "Cegth0285cnhjyuGfhjkm456");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    databaseProperties);
        } catch (SQLException ex) {
            throw new RuntimeException("Can't create connection to database", ex);
        }
    }
}
