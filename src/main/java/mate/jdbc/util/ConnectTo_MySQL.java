package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTo_MySQL implements ConnectTo_DB {
    private static final String WAY_TO_MYSQL_SERVER = "jdbc:mysql://localhost:3306/";
    private static final String PASSWORD = "mate_db";
    private static final String USER = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver", e);
        }
    }

    public Connection getConnection() {
        try {
            String tmp = String.format("%suser=%s&password=%s", WAY_TO_MYSQL_SERVER, USER, PASSWORD);
            return DriverManager.getConnection(tmp);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get connection to DB", e);
        }
    }
}
