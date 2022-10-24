package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exceptions.DataProcessingException;

public class ConnectionMySqlUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load jdbc Driver for MySql",e);
        }
    }

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/manufacturers_db";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1985";

    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user",LOGIN);
            properties.put("password",PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL,properties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB",e);
        }
    }
}
