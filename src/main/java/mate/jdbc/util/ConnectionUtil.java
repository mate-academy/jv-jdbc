package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private Connection connection;

    public ConnectionUtil() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxy_service?serverTimezone=UTC",
                            "root", "aqwa2012");
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection to DB.");
        }
        System.out.println(connection);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Can´t close connection to DB.");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
