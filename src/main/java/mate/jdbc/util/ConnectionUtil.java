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
            Properties dbproperties = new Properties();
            dbproperties.put("user", "root");
            dbproperties.put("password", "1111");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_taxi?serverTimezone=Europe/Kiev", dbproperties);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't create connection to DB ", throwables);
        }
    }
}
