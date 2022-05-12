package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "mate_student";
    private static final String PASSWORD = "Cegth0285cnhjyuGfhjkm456";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxi_service";

    public static Connection getConnection() {
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.put("user", USERNAME);
            databaseProperties.put("password", PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, databaseProperties);
        } catch (SQLException ex) {
            throw new RuntimeException("Can't create connection to database", ex);
        }
    }
}
