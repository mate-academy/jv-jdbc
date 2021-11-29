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
            dbProperties.put("user", "kitti");
            dbProperties.put("password", "qwerty");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + "taxi_service?serverTimezone=UTC", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new connection to DB", e);
        }
    }
}
