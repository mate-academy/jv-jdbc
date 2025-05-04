package mate.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.exception.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC Driver for MySql", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "pass1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturerDB",
                    dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
