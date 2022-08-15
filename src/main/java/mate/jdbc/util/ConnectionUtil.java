package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_NAME = "taxi_service";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB: " + DB_NAME, e);
        }
    }

}
