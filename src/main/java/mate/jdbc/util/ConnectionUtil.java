package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mysql ", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", "root");
            propertiesDb.put("password", "1984");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", propertiesDb);
        } catch (SQLException u) {
            throw new RuntimeException("Can't create connection db", u);
        }
    }
}
