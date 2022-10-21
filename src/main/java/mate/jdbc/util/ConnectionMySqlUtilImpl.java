package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMySqlUtilImpl implements ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc Driver for MySql",e);
        }
    }

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/manufacturers_db";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1985";

    @Override
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user",LOGIN);
            properties.put("password",PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL,properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB",e);
        }
    }
}
