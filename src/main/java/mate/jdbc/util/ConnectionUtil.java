package mate.jdbc.util;

import mate.jdbc.model.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            String message = "Can't load JDBC driver for MySQL";
           throw new DataProcessingException(message, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "357Killer357");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/cars_info_db", dbProperties);
        } catch (SQLException e) {
            String message = "Can't create connection to DB";
            throw new DataProcessingException(message, e);
        }
    }
}
