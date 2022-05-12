package mate.jdbc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.jdbc.model.Manufacturer;

public interface SqlParserService {
    Manufacturer parse(ResultSet resultSet) throws SQLException;
}
