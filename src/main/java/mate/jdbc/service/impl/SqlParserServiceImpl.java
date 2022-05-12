package mate.jdbc.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.SqlParserService;

public class SqlParserServiceImpl implements SqlParserService {
    @Override
    public Manufacturer parse(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new Manufacturer(id, name, country);
    }
}
