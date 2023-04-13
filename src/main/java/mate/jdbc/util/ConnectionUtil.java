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
            throw new RuntimeException("Can not load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties db_properties = new Properties();
            db_properties.put("user", "root");
            db_properties.put("password", "arthurcs123");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", db_properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB", e);
        }
    }
}
