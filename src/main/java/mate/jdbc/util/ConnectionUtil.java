package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "RwMlQrQf";
    private static final String COLUMN_NAME = "user";
    private static final String COLUMN_PASSWORD = "password";

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
            propertiesDB.put(COLUMN_NAME,USER_NAME);
            propertiesDB.put(COLUMN_PASSWORD,USER_PASSWORD);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db",
                    propertiesDB);
        } catch (SQLException e) {
            throw new RuntimeException("Can't open connection", e);
        }
    }
}
