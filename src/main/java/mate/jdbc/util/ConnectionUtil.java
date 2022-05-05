package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Can't load JDBC driver for MySQL!", ex);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "SQL22562");
            String urlString = "jdbc:mysql://localhost:3306/taxi_service";
            return DriverManager.getConnection(urlString, dbProperties);
        } catch (SQLException ex) {
            throw new RuntimeException("Can't create connection to DB!", ex);
        }
    }
}
