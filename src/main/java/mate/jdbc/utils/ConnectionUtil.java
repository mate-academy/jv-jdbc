package mate.jdbc.utils;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public final class ConnectionUtil {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource hikariDataSource = new HikariDataSource();
            hikariDataSource.setJdbcUrl("jdbc:mysql://178.150.196.140:7306/taxiService");
            hikariDataSource.setUsername("mateAcademy");
            hikariDataSource.setPassword("123QWE456rty*");
            dataSource = hikariDataSource;
        }
        return dataSource;
    }
}
