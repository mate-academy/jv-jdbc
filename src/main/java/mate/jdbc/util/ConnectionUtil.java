package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "88888888";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find driver for MySQL");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
