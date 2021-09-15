package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement
                    .executeQuery("SELECT * FROM library_db.manufacturers;");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                boolean isDeleted = resultSet.getObject("is_deleted", Integer.class) != 0;
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setCountry(country);
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setIsDeleted(isDeleted);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all formats from DB", e);
        }
        return allManufacturers;
    }
}
