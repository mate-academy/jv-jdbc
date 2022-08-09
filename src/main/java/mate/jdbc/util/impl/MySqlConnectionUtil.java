package mate.jdbc.util.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.util.ConnectionUtil;

public class MySqlConnectionUtil implements ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.setProperty("user", "root");
            dbProperties.setProperty("password", "1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
