package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
    private static final String DB_PATH = "jdbc:mysql://localhost:3306/taxi_db";
    private static final String DB_PASSWORD = "sania517";
    private static final String DB_USER_NAME = "root";
    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver was loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.error("Can not load driver DB. " + e);
            throw new RuntimeException("Can not load driver DB. " + e);
        }
    }

    public static Connection getConnection() {
        try {
            logger.info("getConnection was called.");
            Properties dbProperties = new Properties();
            dbProperties.put("user", DB_USER_NAME);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_PATH, dbProperties);
        } catch (SQLException e) {
            logger.info("getConnection was failed.", e);
            throw new RuntimeException("Can not establish connection to the server. PATH: "
                    + DB_PATH, e);
        }
    }
}
