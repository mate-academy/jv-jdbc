package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSource {
    private static final Logger log = LogManager.getLogger(DataSource.class);

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver", e);
        }

        try {
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            log.info("Success conection to database: taxi");
            return DriverManager.getConnection("jdbc:postgresql://localhost/taxi", props);

        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection for database", e);
        }
    }
}
