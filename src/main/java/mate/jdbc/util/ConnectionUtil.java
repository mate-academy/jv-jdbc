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
            throw new RuntimeException("Can't load driver JDBC", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProp = new Properties();
            dbProp.put("user", "root");
            dbProp.put("password", "2604");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturer_db",
                    dbProp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
