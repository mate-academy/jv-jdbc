package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.dao.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("It`s impossible to load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Kotofei1993");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/library_db", dbProperties);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t create connection to DB", throwables);
        }
    }
}

