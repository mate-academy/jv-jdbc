package mate.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.exception.DataProccesingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProccesingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "java123");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db",
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProccesingException("Can't create connection to DataBase", e);
        }
    }
}
