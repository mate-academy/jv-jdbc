package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String driverPath = "com.mysql.cj.jdbc.Driver";
    private static final String mySQLUrl = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(driverPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver from MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user","root");
            dbProperties.put("password","bloodystorm");
            return DriverManager.getConnection(mySQLUrl,
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
