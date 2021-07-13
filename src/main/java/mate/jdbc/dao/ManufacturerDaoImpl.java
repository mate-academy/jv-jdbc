package mate.jdbc.dao;

import mate.jdbc.connection.ConnectionUtil;
import mate.jdbc.model.Manufacturer;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
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
            throw new RuntimeException("Can't get all manufacturer from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            getAllManufacturersStatement.setString(1, manufacturer.getName());
            getAllManufacturersStatement.setString(2, manufacturer.getCountry());
            getAllManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = getAllManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String insertManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement = connection.prepareStatement(insertManufacturerRequest)) {
            getAllManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer to DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String insertManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement =
                     connection.prepareStatement(insertManufacturerRequest)) {
            getAllManufacturersStatement.setString(1, manufacturer.getName());
            getAllManufacturersStatement.setString(2, manufacturer.getCountry());
            getAllManufacturersStatement.setLong(3, manufacturer.getId());
            getAllManufacturersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement =
                     connection.prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            getAllManufacturersStatement.setLong(1, id);
            return getAllManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer to DB", e);
        }
    }
}
