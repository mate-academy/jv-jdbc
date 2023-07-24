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
            throw new RuntimeException("Can't load JDBC driver for MySQL ", e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "user");
            dbProperties.put("password", "acdACD123");
            return DriverManager.getConnection("jdbc:mysql://192.168.50.253:3306/library_db", dbProperties);
        } catch (
                SQLException e) {
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }
}
