package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USERSNAME = "root";
    private static final String PASSWORD = "1123";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USERSNAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(
                    URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection to DB", e);
        }
    }
}
