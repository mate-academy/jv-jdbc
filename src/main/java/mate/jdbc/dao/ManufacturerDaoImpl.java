package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertDataRequest = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (PreparedStatement createDataStatement = ConnectionUtil.getConnection()
                .prepareStatement(insertDataRequest, Statement.RETURN_GENERATED_KEYS)) {
            createDataStatement.setString(1, manufacturer.getName());
            createDataStatement.setString(2, manufacturer.getCountry());
            createDataStatement.executeUpdate();
            ResultSet generatedKeys = createDataStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            String message = "Can't insert data to DB";
            throw new DataProcessingException(message, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Statement getAllFormatsStatement = ConnectionUtil.getConnection().createStatement()) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery("SELECT * FROM manufacturers");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            String message = "Can't get all formats from DB";
            throw new DataProcessingException(message, e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
