package mate.jdbc.dao;

import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert Manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement selectManufacturerStatement =
                     connection.prepareStatement(selectRequest, Statement.RETURN_GENERATED_KEYS)) {
            selectManufacturerStatement.setLong(3, id);
            ResultSet resultSet = selectManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update Manufacturer from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement selectAllManufacturer = connection.createStatement()) {
            ResultSet resultSet = selectAllManufacturer.executeQuery("SELECT * FROM manufacturers WHERE is_deleted = true;");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all Manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(3, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update Manufacturer from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete Manufacturer from DB", e);
        }
    }
}
