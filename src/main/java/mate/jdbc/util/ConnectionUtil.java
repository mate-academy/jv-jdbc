package mate.jdbc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "taxi_service_db";
    private static final Properties DB_PROPERTIES;
    private static final String SCRIPT_INIT_FILE_PATH = "src/main/resources/init_db.sql";
    private static final String SCRIPT_CREATE_FILE_PATH = "src/main/resources/create_db.sql";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DB_PROPERTIES = new Properties();
            DB_PROPERTIES.put("user", "admin");
            DB_PROPERTIES.put("password", "admin123");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager
                    .getConnection(URL + DATABASE_NAME,
                            DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }

    public static void executeScript() {
        if (createDb()) {
            try (Connection connection = getConnection();
                    Statement statement = connection.createStatement();
                    BufferedReader reader = new BufferedReader(
                            new FileReader(SCRIPT_INIT_FILE_PATH))) {
                String line;
                StringBuilder scriptBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    scriptBuilder.append(line).append("\n");
                }
                String script = scriptBuilder.toString();
                statement.execute(script);
            } catch (Exception e) {
                throw new RuntimeException("Can't execute init script", e);
            }
        }
    }

    private static boolean createDb() {
        try (Connection connection = DriverManager.getConnection(URL, DB_PROPERTIES);
                Statement statement = connection.createStatement();
                BufferedReader reader = new BufferedReader(
                        new FileReader(SCRIPT_CREATE_FILE_PATH))) {
            ResultSet resultSet =
                    statement.executeQuery("SHOW DATABASES LIKE '" + DATABASE_NAME + "'");
            if (resultSet.next()) {
                logger.debug("Database exists");
                return false;
            }
            String line;
            StringBuilder scriptBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                scriptBuilder.append(line).append("\n");
            }
            String script = scriptBuilder.toString();
            statement.execute(script);
        } catch (Exception e) {
            throw new RuntimeException("Can't create db", e);
        }
        return true;
    }
}
