package mate.jdbc.dao;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate(insertManufacturerRequest);
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DataBase", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String deleteManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatement =
                     connection.prepareStatement(deleteManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long idManufacturer = resultSet.getObject("id", Long.class);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturer.setId(idManufacturer);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturerStatement =
                     connection.prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setCountry(country);
                manufacturer.setName(name);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DataBase", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement
                     = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement deleteManufacturerStatement =
                connection.prepareStatement(deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DataBase", e);
        }
    }
}
