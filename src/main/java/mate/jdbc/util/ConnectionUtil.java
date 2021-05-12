package mate.jdbc.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/MySQL_DB.properties";

    static {
        try {
            properties.load(new FileReader(PROPERTIES_FILE_PATH));
            Class.forName((String) properties.get("db.driver"));
        } catch (ClassNotFoundException | IOException exception) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", exception);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection((String) properties.get("db.url"), properties);
        } catch (SQLException exception) {
            throw new RuntimeException("Can't create connection to DB "
                    + properties.get("schema"), exception);
        }
    }
}
