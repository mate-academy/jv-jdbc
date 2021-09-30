package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.custromexception.MyCustomException;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "************";
    // deleted password because I use it not only here
    private static final String LOCAL_HOST = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String LINK_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(LINK_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new MyCustomException("Can't load JDBC driver for MySQl!", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(LOCAL_HOST, dbProperties);
        } catch (SQLException e) {
            throw new MyCustomException("Can't create Connection to DB!", e);
        }
    }
}
