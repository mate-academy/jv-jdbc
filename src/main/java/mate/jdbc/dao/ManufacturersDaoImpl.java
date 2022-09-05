package mate.jdbc.dao;

import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturers = connection.createStatement()){
            ResultSet resultSet = getAllManufacturers
                    .executeQuery("SELECT * from manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all formats from DB", e);
        }
        return allManufacturers;
    }
}
