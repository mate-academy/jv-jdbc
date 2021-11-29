package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.lib.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_CONNECTION = "jdbc:mysql://localhost:3306/"
            + "taxi_service?serverTimezone=UTC";
    private static final String DB_LOGIN = "kitti";
    private static final String DB_PASSWORD = "qwerty";

    static {
        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_LOGIN);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(MYSQL_CONNECTION, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new connection to DB", e);
        }
    }
}
