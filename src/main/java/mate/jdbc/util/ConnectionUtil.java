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
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties dbproperties = new Properties();
            dbproperties.put("user", "root");
            dbproperties.put("password", "mt110711@");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service_db",
                    dbproperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to database", throwables);
        }
    }
}
