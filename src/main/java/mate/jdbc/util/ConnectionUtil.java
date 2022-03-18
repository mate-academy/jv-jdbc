package mate.jdbc.util;

import mate.jdbc.exeptions.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_ADDRESS = "jdbc:mysql://localhost:3306/init_db";
    private static final String USER = "root";
    private static final String PASSWORD = "HardPasSword";

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
                    DB_CONNECTION_ADDRESS, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect ro DB", e);
        }
        return connection;
    }

}
