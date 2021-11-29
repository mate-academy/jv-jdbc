package mate.jdbc.services.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.jdbc.models.User;

public class ConnectorImpl implements Connector {
    private static final String DB_NAME = "taxi_db";
    private final Properties properties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for mysql.", e);
        }
    }

    public ConnectorImpl(User user) {
        properties.put("user", user.getLogin());
        properties.put("password", user.getPassword());
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + DB_NAME, properties);
    }
}
