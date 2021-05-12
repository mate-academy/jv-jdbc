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
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "business_user");
            properties.put("password", "87654321");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/business_db", properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection with DB", e);
        }
    }
}
