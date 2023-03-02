package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    private static Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ConnectionUtil.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mate", "root", "1111");
        }

        return connection;
    }
}
