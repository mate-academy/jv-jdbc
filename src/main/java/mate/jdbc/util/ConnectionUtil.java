package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String LOGIN = "root";
    private static final String PASSWORD = "********";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", LOGIN);
            dbProperties.put("password", PASSWORD);
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/manufacturers_scheme"
                                    + "?serverTimezone=UTC",
                            dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to manufacturers DB", e);
        }
    }
}
