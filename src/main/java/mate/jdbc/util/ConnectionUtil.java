package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String LOGIN = "root";
    public static final String PASSWORD = "password";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MANUFACTURER_DB = "jdbc:mysql://localhost:3306/manufacturer_db";
    public static final String LOGIN_PROPERTY = "user";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String EXCEPTION_MESSAGE = "Can't connect to DB";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't found " + JDBC_DRIVER, e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(LOGIN_PROPERTY, LOGIN);
            dbProperties.put(PASSWORD_PROPERTY, PASSWORD);
            return DriverManager
                    .getConnection(MANUFACTURER_DB, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException(EXCEPTION_MESSAGE, e);
        }
    }
}
