package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
   private static final String URL = "jdbc:mysql://localhost:3306/taxi_service_db";
   private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String USER = "root";
   private static final String PASSWORD = "Artik25942";
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException("Can't load sql Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("can't create connection", e);
        }
    }
}
