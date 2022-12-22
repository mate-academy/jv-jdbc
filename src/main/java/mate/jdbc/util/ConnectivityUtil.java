package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectivityUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "*******");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service_db",
                    properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't open connection to DB", e);
        }
    }
}
