package mate.jdbc.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver from MySQL", e);
        }
    }

    public static java.sql.Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1111");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturer_db",
                    dbProperties);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create connection to DB", throwables);
        }
    }
}
