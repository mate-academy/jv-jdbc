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
            String dbUrl = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7630939";
            Properties dbProperties = new Properties();
            dbProperties.put("user", "sql7630939");
            dbProperties.put("password", "HmUQTbNf6K");
            return DriverManager.getConnection(dbUrl, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
