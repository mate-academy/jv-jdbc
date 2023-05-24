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
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "taxi_service");
            dbProperties.put("password", "jXmXIUZ@t66.cq7U");
            return
              DriverManager.getConnection("jdbc:mysql//localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t open connection ", e);
        }
    }
}
