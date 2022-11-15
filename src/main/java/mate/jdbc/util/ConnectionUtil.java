package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String ACTUAL_PASSPORT = "3fVf}iD]w}rHc6FMvV-5Xuly";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver "
                    + "for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER, USER_NAME);
            dbProperties.put(PASSWORD, ACTUAL_PASSPORT);
            return DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/taxi_service_db",
                            dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection "
                    + "to DB", e);
        }
    }
}
