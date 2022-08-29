package mate.jdbc.util;

import mate.jdbc.db.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getDbConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC Driver for MySQL.", e);
        }

        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "JAVAMate22/07");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/taxi_service_db", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB.",e);
        }
    }
}
