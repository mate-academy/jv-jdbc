package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.lib.exception.DataProcessingException;

public class ConnectionMySql {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't get MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "20152015a");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                + "taxi_service?serverTimezone=UTC", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new connection to DB", e);
        }
    }
}
