package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProgressingException;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "maks";
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProgressingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", USER);
            propertiesDb.put("password", PASSWORD);
            return DriverManager.getConnection(URL_CONNECTION,
                    propertiesDb);
        } catch (SQLException e) {
            throw new DataProgressingException("Can't create connection to DB", e);
        }
    }
}
