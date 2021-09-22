package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Predicate;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver for MySQL", e);
        }
    }
    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connected to jv-jdbc base", e);
        }
    }
}
