package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(createManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            while (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't insert manufacturer: "
                    + manufacturer, exception);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest + id);) {
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Optional<Manufacturer> manufacturerOptional = null;
            Manufacturer manufacturer = new Manufacturer();
            while (resultSet.next()) {
                manufacturer = initializeManufacturer(resultSet);
            }
            manufacturerOptional = Optional.of(manufacturer);
            return manufacturerOptional;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get manufacture by id = "
                    + id + " from DB", exception);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String allManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(allManufacturerRequest);) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = initializeManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't get all manufacturers from DB", exception);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't update manufacture: "
                    + manufacturer, exception);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturers SET "
                + "is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufactureStatement =
                        connection.prepareStatement(deleteManufactureRequest)) {
            deleteManufactureStatement.setLong(1, id);
            return deleteManufactureStatement.executeUpdate() >= 1 ? true : false;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can't delete record with id: " + id, exception);
        }
    }

    private Manufacturer initializeManufacturer(
            ResultSet resultSet) throws SQLException {
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        Long manufacturerId = resultSet.getObject("id", Long.class);
        return new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
    }
}
