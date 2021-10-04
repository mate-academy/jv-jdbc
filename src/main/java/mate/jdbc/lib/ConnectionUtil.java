package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/taxi_db\"";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ditro1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC Driver for MySQL" + e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user",USERNAME);
            dbProperties.put("password",PASSWORD);
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException throwable) {
            throw new RuntimeException("Can`t create connection DB", throwable);
        }
    }
}
