package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String CLASS_NAME_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl;
    private static String userName;
    private static String userPassword;

    static {
        //set default connections data
        dbUrl = "jdbc:mysql://localhost:3306/taxi_service";
        userName = "root";
        userPassword = "11111";
        try {
            Class.forName(CLASS_NAME_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver '" + CLASS_NAME_DRIVER
                    + "' for MySQL", e);
        }

    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static void setDbUrl(String dbUrl) {
        ConnectionUtil.dbUrl = dbUrl;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        ConnectionUtil.userName = userName;
    }

    public static void setUserPassword(String userPassword) {
        ConnectionUtil.userPassword = userPassword;
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", userName);
            dbProperties.put("password", userPassword);
            return DriverManager.getConnection(dbUrl, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB: "
                    + dbUrl, e);
        }
    }
}
