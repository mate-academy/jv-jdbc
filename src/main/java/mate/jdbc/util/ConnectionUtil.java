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
            throw new RuntimeException("Can't load JDBS driver for MySQL!", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dpProperties = new Properties();
            dpProperties.put("user", "root");
            dpProperties.put("password", "23071998Dima");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306"
                    + "/hw_jdbc?useUnicode=true&serverTimezone=UTC", dpProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
