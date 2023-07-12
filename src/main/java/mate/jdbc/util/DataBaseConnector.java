package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector {
    private static final String USER = "root";
    private static final String PASSWORD = "PASSWORD";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try  {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi-service", USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection", e);
        }
    }
}


