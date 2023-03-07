package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String LOGIN_FROM_DB = "root";
    public static final String PAASSWORD_FROM_DB = "Aa755057265";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }
    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", LOGIN_FROM_DB);
            dbProperties.put("password", PAASSWORD_FROM_DB);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to db", throwables);
        }
    }
}
