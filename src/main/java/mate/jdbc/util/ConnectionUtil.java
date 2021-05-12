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
            throw new RuntimeException("Can't get JDBC driver",e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDB = new Properties();
            propertiesDB.put("user","root");
            propertiesDB.put("password","RwMlQrQf");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db",
                    propertiesDB);
        } catch (SQLException e) {
            throw new RuntimeException("Can't open connection", e);
        }
    }
}
