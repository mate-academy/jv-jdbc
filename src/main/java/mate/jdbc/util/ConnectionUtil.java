package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "business_user";
    private static final String USER_PASSWORD = "87654321";
    private static final String URL_TO_DB = "jdbc:mysql://localhost:3306/business_db";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver ", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER_NAME);
            properties.put("password", USER_PASSWORD);
            return DriverManager
                    .getConnection(URL_TO_DB, properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection with DB ", e);
        }
    }
}
