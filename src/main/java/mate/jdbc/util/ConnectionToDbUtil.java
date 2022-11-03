package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDbUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }
    public static Connection getConnection(Properties properties) {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service-db", properties);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to DB went wrong", e);
        }
        return null;
    }
}
