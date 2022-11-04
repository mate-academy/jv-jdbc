package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionUtil {
    private static final String PROPERTY_FILE_NAME = "config";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
        String url = resourceBundle.getString("url");
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB");
        }
    }
}
