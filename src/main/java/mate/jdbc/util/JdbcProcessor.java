package mate.jdbc.util;

import java.sql.PreparedStatement;

public interface JdbcProcessor {
    Object process(PreparedStatement statement);
}
