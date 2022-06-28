package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver for MYSQL",e);
        }
    }

    public static Connection getConnection() {

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user","java");
            dbProperties.put("password","java");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxidb",dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB",throwables);
        }

    }

}
