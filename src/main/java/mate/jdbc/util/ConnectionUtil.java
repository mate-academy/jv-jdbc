package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver for MYSQL");
        }

    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "aqwa2012");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxy_service?serverTimezone=UTC", dbProperties);

        } catch (SQLException throwables) {
            throw new RuntimeException("Cant create connection to DB");
        }
        return connection;
    }
}
