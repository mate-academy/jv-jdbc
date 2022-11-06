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
            String connect = "jdbc:mysql://localhost:3306/manufacturer_db";
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "2548117Andrei4uk");
            return DriverManager.getConnection(connect, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
