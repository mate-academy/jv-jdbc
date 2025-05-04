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
        String insertManufacturerRequest = "INSERT INTO manufacturer(name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection.prepareStatement(
                           insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer in DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturer WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getManufacturerStatement = connection.prepareStatement(
                        getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllManufacturersRequest =
                "SELECT * FROM manufacturer WHERE is_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement =
                        connection.prepareStatement(selectAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseResultSet(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturer SET name = ?, country = ? where id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setObject(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update manufacturer from DB" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturer SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB" + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject(1, Long.class);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't parse ResultSet to manufacturer: " + resultSet, e);
        }
    }
}
