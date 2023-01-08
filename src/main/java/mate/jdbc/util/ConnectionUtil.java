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
            throw new RuntimeException("Can not load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", "root");
            propertiesDb.put("password", "12345678");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/manufacture", propertiesDb);

        } catch (SQLException throwables) {
            throw new RuntimeException("Can not create connection to DB", throwables);
        }
    }
}
