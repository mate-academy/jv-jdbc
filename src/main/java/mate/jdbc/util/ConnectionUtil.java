package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/manufacturer_db";
    private static final String USER = "root";
    private static final String PASSWORD = "x67i9#VYL%2X(*vd";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL: " + DRIVER, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER);
            dbProperties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(URL, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can`t create connection to DB ,URL: "
                    + URL + " user: " + USER, throwables);
        }

    }
}
