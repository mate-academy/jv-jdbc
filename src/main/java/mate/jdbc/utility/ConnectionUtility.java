package mate.jdbc.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtility {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't download JDBC driver " + e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbproperties = new Properties();
            dbproperties.put("user", "root");
            dbproperties.put("password", "1234");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ibit_db", dbproperties);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't connect  to database " + throwables);
        }
    }
}
