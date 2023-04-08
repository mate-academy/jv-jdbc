package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            // завантажити драйвер
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can’t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            // стоврити обьєкт з логіном та паролем для доступбу до БД
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "Dtc3mvb6h_");
            // створити конекшен
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi_service", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can’t createthe connection from DB", e);
        }
    }
}
