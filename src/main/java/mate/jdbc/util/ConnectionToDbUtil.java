package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDbUtil {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_service_db";
    private static final String PROPERTIES_FILE = "src/main/resources/DBProperties";
    private static final Properties properties;

    static {
        try {
            Class.forName(MYSQL_DRIVER);
            properties = DbPropertiesFileReader
                    .getPropertiesFrom(PROPERTIES_FILE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DB_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to DB went wrong", e);
        }
    }
}
