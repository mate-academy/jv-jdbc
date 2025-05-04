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
            throw new RuntimeException("Can`t found the class", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1111");
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get connection to db", e);
        }
    }
}
