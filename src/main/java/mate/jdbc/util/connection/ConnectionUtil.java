package mate.jdbc.util.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver from MySQL ", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "12249251Ll8?");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/db_taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t connect to DB ", e);
        }
    }
}
