package mate.jdbc.utils;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public final class ConnectionUtil {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource hikariDataSource = new HikariDataSource();
            hikariDataSource.setJdbcUrl("jdbc:mysql://178.150.196.140:7306/taxi_service");
            hikariDataSource.setUsername("mateAcademy");
            hikariDataSource.setPassword("123QWE456rty*");
            dataSource = hikariDataSource;
        }
        return dataSource;
    }
}
