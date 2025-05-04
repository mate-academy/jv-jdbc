package mate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/taxi_service";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    CONNECTION_ADDRESS, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect ro DB", e);
        }
        return connection;
    }
}
