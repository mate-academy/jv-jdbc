package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PATH_TO_DB = "jdbc:mysql://localhost:3306/manufacturer_db";
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "101351013");
            return DriverManager.getConnection(PATH_TO_DB, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection", e);
        }
    }
}
