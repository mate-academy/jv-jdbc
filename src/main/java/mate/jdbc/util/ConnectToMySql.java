package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectToMySql implements ConnectToDB {
    private static final String WAY_TO_MYSQL_DB = "jdbc:mysql://localhost:3306/taxi_service";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver", e);
        }
    }

    public Connection getConnection() {
        try {
            Properties user = new Properties();
            user.put("user", "root");
            user.put("password", "matedb");
            return DriverManager.getConnection(WAY_TO_MYSQL_DB, user);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get connection to DB", e);
        }
    }
}
