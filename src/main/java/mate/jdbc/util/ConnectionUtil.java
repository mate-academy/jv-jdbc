package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "123321");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturers_db",
                    dbProperties);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't establish connection to DB via following "
                    + "link: jdbc:mysql://localhost:3306/manufacturers_db", throwable);
        }
    }
}
