package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.custom.Exception;

public class UtilClass {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("can't load JDBCDrivers", e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "12345678");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new Exception("can't create connection", e);
        }
    }
}
