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
            Properties dataBaseProperties = new Properties();
            dataBaseProperties.put("user", "root");
            dataBaseProperties.put("password", "2604");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturer_db",
                    dataBaseProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get connection to manufacturer_db");
        }
    }
}
