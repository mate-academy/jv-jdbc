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
            throw new RuntimeException("Can`t load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "OkC0mputer");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturers",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t connect to DB", e);
        }
    }
}
