package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final String URL = "jdbc:mysql://localhost:3306/manufacture";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties propertiesDb = new Properties();
            propertiesDb.put("user", USER_NAME);
            propertiesDb.put("password", USER_PASSWORD);
            return DriverManager.getConnection(
                    URL, propertiesDb);

        } catch (SQLException throwables) {
            throw new RuntimeException("Can not create connection to DB", throwables);
        }
    }
}
