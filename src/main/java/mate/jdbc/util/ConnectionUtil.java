package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String USER = "your_user";
    private static final String PASSWORD = "your_password";

    private static final String HOST = "your_host";
    private static final String PORT = "0000";
    private static final String DATABASE = "manufacturer_db";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PASSWORD);
            return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/"
                            + DATABASE, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creat connection to DB", e);
        }
    }
}
