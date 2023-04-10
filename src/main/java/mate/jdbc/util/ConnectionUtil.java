package mate.jdbc.util;

import mate.jdbc.exception.DataProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_PROPERTIES_NAME = "datasource.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConnectionUtil.class
                .getClassLoader()
                .getResourceAsStream(DB_PROPERTIES_NAME)) {
            if (inputStream == null) {
                throw new DataProcessingException("Can't find file " + DB_PROPERTIES_NAME);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new DataProcessingException("Can't read from "
                    + DB_PROPERTIES_NAME + " file. " + e);
        }
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to database", e);
        }
    }
}
