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
            throw new DataProcessingException("Can't load a driver JDBC for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProp = new Properties();
            dbProp.put("user", "root");
            dbProp.put("password", "qwerty123");
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taxi_service_db",
                    dbProp);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to DB", e);
        }
    }
}
