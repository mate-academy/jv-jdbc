package mate.jdbc.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL;
    private static final String NAME;
    private static final String PASSWORD;

    static {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(new FileInputStream("src/main/resources/application.properties"));
            Class.forName(dbProperties.getProperty("db.driver"));
            URL = dbProperties.getProperty("db.url");
            NAME = dbProperties.getProperty("db.name");
            PASSWORD = dbProperties.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create connection to DB", throwables);
        }
    }
}
