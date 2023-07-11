package mate.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties db = new Properties();
            db.put("user", "root");
            db.put("password", "PASSWORD HERE");
            DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", db);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection", e);
        }
    }
}
