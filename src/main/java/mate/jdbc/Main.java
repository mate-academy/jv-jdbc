package mate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

    }

    public static class ConnectionUtil {
        static {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Can't load JDBC driver fo MySQL", e);
            }
        }
        public static Connection getConnection() {
            try {
                Properties dbProperties = new Properties();
                dbProperties.put("user", "root");
                dbProperties.put("password", "1234");
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", dbProperties);
            } catch (SQLException e) {
                throw new RuntimeException("Can't create connection to DB", e);
            }
        }
    }
}
