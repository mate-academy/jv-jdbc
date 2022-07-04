package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_BD = "root";
    private static final String PASSWORD_BD = "1234";
    private static final String URL_BD = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC could not be loaded for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_BD);
            dbProperties.put("password", PASSWORD_BD);
            return DriverManager.getConnection(URL_BD, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("The connection was not established to BD", e);
        }
    }
}
