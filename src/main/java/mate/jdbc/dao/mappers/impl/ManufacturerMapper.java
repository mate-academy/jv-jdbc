package mate.jdbc.dao.mappers.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.jdbc.dao.mappers.Mapper;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.model.Manufacturer;

public class ManufacturerMapper implements Mapper<Manufacturer> {
    @Override
    public Manufacturer apply(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create object",e);
        }
    }
}
