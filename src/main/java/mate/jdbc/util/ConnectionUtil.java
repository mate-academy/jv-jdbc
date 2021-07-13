package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/manufacturer_db";

    static {
        final String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver " + driver + " for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, "root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection to DB " + URL, e);
        }
    }
}
