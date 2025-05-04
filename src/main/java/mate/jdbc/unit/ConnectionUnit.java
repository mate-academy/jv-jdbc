package mate.jdbc.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUnit {
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Data50Base42";
    private static final String URL = "jdbc:mysql://localhost:3306/db_taxi_service";

    static {
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver for sql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", USER_NAME);
            properties.put("password", PASSWORD);
            return DriverManager
                    .getConnection(URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to db", e);
        }
    }
}
