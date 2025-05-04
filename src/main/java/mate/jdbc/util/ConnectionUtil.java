package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String DB_LOGIN = "root";
    private static final String DB_PASSWORD = "0660106493";
    private static final String LOCAL_HOST = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for mySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_LOGIN);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(LOCAL_HOST, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB", e);
        }
    }
}
