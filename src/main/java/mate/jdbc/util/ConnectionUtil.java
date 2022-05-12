package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "root12341234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver for mySQL", e);
        }
    }

    public static Connection getConnection() {
        logger.info("getting connection to DB...");
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi_service_schema", dbProperties);
            logger.info("successful connection to DB -> {}", connection.getCatalog());
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
