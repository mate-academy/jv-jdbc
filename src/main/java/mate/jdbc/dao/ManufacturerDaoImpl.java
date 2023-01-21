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
        String insertManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturers to DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerById =
                         connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerById.setLong(1, id);
            ResultSet resultSet = getManufacturerById.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturer =
                "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturerStatement =
                         connection.prepareStatement(getAllManufacturer)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB "
                    + allManufacturers, e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE  id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                         connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturers to DB "
                    + updateManufacturerRequest, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement =
                         connection.prepareStatement(deleteManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturers from DB "
                    + deleteManufacturerRequest, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) {
        try {
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            Long manufacturerId = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
            manufacturer.setCountry(manufacturerCountry);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse manufacturer from ResultSet "
                    + resultSet, e);
        }
    }
}
