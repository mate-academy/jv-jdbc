package mate.jdbc.util;

import exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    static {
        try {
            logger.info("Loading JDBC driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver fo MySQL", e);
        }
    }

    public static Connection getConnection() {
        logger.info("Calling getConnection");
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1234");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxi_service_db?serverTimezone=UTC",
                    dbProperties);
        } catch (SQLException e) {
            logger.error("Can't create connection to DB", e);
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
