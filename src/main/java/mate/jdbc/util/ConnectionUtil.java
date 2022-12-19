package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        static {
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
                throw new RuntimeException("Can't load JDBC Driver for MYSQL", e);
        try {

            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Omanair2");
            return DriverManager.getConnection("jdbc:mysql:localhost:3306//library_db", dbProperties);

        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
            }
        }

    }
}
