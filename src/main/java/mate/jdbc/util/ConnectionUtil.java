package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String JDBC_DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_CONNECTION_PATH = "jdbc:mysql://localhost:3306/taxi_service";
    public static final String PASSWORD = "bvf5u8";
    public static final String LOGIN = "root";
    public static final String PASSWORD_WORD = "password";
    public static final String USER_WORD = "user";
    static {
        try {
            Class.forName(JDBC_DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't create JDBC drive for mySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_WORD, LOGIN);
            dbProperties.put(PASSWORD_WORD, PASSWORD);
            return DriverManager
                    .getConnection(JDBC_CONNECTION_PATH, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't "
                    + "create connection to DB in DriverManager", throwables);
        }
    }
}
