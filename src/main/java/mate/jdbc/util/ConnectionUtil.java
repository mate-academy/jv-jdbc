package mate.jdbc.util;

import mate.jdbc.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "mateAcademy2020");
            return DriverManager.getConnection("jdbc:mysql:"
                            + "//localhost:3306/taxi_db?serverTimezone=UTC",
                    dbProperties);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't create connection to DB", throwable);
        }
    }
}
