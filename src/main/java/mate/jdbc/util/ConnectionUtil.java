package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.dao.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_TYPE = "root";
    private static final String PASSWORD = "MySQLRootFrankobike2004";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/taxi_db";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {

            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_TYPE);
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(DATABASE_URL, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create connection to DB: " + DATABASE_URL, e);
        }
    }
}
