package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.lib.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final Properties dbProperties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't find Driver", e);
        }
        dbProperties.put("user", "root");
        dbProperties.put("password", "MySQLPassword");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get database access", e);
        }
    }
}
