package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.dao.DataProcessingException;

public class ConnectionUtilities {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t download driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "gp18rn56");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                  properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get connection", e);
        }
    }
}

