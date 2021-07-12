package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Gfhfyjbr222");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/manufacturer_db", dbProperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver for MySQL ", e);
        } catch (SQLException e) {
            throw new RuntimeException("Cant get connection to DB ", e);
        }
    }
}
