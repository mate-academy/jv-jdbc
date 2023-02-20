package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturers(name, country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(insertManufacturer,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, manufacturer.getName());
            createManufacturer.setString(2, manufacturer.getCountry());
            createManufacturer.executeUpdate();
            ResultSet generatedKeys = createManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer to DB ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String selectRequest = "SELECT * FROM manufacturers WHERE isDeleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer =
                        connection.prepareStatement(selectRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet =
                    getManufacturer.executeQuery();
            while (resultSet.next()) {
                manufacturer = initializationManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from DB by id " + id, e);
        }
        if (manufacturer.getId() == null) {
            throw new DataProcessingException("Manufacturer by id " + id + " not found or deleted.",
                    new RuntimeException());
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE isDeleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFormat = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getFormat.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(initializationManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB ", e);
        }
        if (allManufacturers.isEmpty()) {
            throw new DataProcessingException("Manufacturer no found", new RuntimeException());
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String deleteRequest =
                "UPDATE manufacturers SET name = 'Ferrari' WHERE isDeleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(deleteRequest)) {
            if (manufacturer.getId() == null) {
                throw new DataProcessingException(
                        "Manufacturer not found or deleted or not specified id.",
                        new RuntimeException());
            }
            createManufacturer.setLong(1, manufacturer.getId());
            createManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't updated manufacturer ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET isDeleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturer.setLong(1, id);
            return createManufacturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't deleted manufacturer from DB by id " + id, e);
        }
    }

    private Manufacturer initializationManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
        } catch (SQLException e) {
            throw new RuntimeException("Can't initialization manufacturer", e);
        }
        return manufacturer;
    }
}
