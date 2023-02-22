package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private final static String MYSQLDB_PATH = "jdbc:mysql://localhost:3306/taxi-service_db";
    private final static String MYSQLDB_USERNAME = "root";
    private final static String MYSQLDB_PASSWORD = "pass123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", MYSQLDB_USERNAME);
            dbProperties.put("password", MYSQLDB_PASSWORD);
            return DriverManager.getConnection(MYSQLDB_PATH, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
