package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProcessingException;

public class ConnectionUtil {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";
    public static final String PATH_TO_DATABASE = "jdbc:mysql://localhost:3306/store_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(PATH_TO_DATABASE,
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to DB ", e);
        }
    }
}
