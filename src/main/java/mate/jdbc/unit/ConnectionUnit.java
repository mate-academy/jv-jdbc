package mate.jdbc.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUnit {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver for sql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "Data50Base42");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/db_taxi_service", properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to db", e);
        }
    }
}
