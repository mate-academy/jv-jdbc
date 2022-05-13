package mate.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSource {
    private static final Logger log = LogManager.getLogger(DataSource.class);
    private final String dbUrl;
    private final String ip;
    private final String port;
    private final String alias;
    private final String userDB;
    private final String pass;

    public DataSource() {
        dbUrl = "";
        ip = "";
        port = "";
        alias = "";
        userDB = "";
        pass = "";
    }

    public DataSource(Properties properties) throws ClassNotFoundException {
        dbUrl = properties.getProperty("DB_URL");
        ip = properties.getProperty("IP");
        port = properties.getProperty("PORT");
        alias = properties.getProperty("ALIAS");
        userDB = properties.getProperty("USERDB");
        pass = properties.getProperty("PASS");
        Class.forName(properties.getProperty("CLASS"));
    }

    public Connection getConnection() throws SQLException {
        log.info("Success conection //" + ip + ":" + port + " database: " + alias);
        return DriverManager.getConnection(dbUrl
                + "://" + ip + ":" + port + "/"
                + alias, userDB, pass);
    }
}
