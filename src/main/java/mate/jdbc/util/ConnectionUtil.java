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
            throw new DataProcessingException("Couldn't load JDBC driver fro MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "0413");
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/manufacturers_schema",
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create connection to DataBase", e);
        }
    }
}
