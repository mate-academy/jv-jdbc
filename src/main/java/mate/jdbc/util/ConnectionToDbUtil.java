package mate.jdbc.util;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionToDbUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }
    public static Connection getConnection(Properties properties) {
        try {

        }
        return null;
    }
}
