package mate.jdbc.lib.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySql", e);
        }
    }

    public static Connection getConnect() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", "root");
            propertiesDb.put("password", "my-secret-pw");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service",
                    propertiesDb);
        } catch (SQLException e) {
            throw new RuntimeException(" Can't create connection to DB ", e);
        }
    }

}
