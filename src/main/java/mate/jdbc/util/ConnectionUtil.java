package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PATH_TO_DB = "jdbc:mysql://localhost:3306/taxiservice_db";
    private static final String USER_LITERAL = "User";
    private static final String USER = "root";
    private static final String PASSWORD_LITERAL = "Password";
    private static final String PASSWORD = "4187";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for mySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put(USER_LITERAL, USER);
            dbProperties.put(PASSWORD_LITERAL, PASSWORD);
            return DriverManager.getConnection(PATH_TO_DB, dbProperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can`t create connection to DB", throwables);
        }
    }
}
