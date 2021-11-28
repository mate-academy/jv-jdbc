package mate.jdbc.services.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {
    Connection connect() throws SQLException;
}
