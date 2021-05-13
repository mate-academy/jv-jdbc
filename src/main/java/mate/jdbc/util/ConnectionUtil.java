package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "RwMlQrQf";
    public static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't get JDBC driver",e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDB = new Properties();
            propertiesDB.put("user",USER_NAME);
            propertiesDB.put("password",USER_PASSWORD);
            return DriverManager.getConnection(URL,
                    propertiesDB);
        } catch (SQLException e) {
            throw new RuntimeException("Can't open connection", e);
        }
    }
}
