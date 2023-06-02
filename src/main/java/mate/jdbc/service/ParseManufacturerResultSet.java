package mate.jdbc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.jdbc.models.Manufacturer;

public class ParseManufacturerResultSet {

    public Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }

}
