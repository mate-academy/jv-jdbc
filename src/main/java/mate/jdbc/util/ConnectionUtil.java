package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProps = new Properties();
        dbProps.setProperty("user", "root");
        dbProps.setProperty("password", "root");
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/study_db", dbProps);
        } catch (SQLException e) {
            throw new RuntimeException("Cant open connection to DB");
        }
    }
}
