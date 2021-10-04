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
        String insertQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(insertQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement =
                        connection.prepareStatement(getQuery)) {
            getByIdStatement.setLong(1, id);
            ResultSet generatedManufacturer = getByIdStatement.executeQuery();
            if (generatedManufacturer.next()) {
                Manufacturer manufacturer = getManufacturer(generatedManufacturer);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllQuery)) {
            ResultSet generatedManufacturers = getAllManufacturersStatement.executeQuery();
            while (generatedManufacturers.next()) {
                Manufacturer manufacturer = getManufacturer(generatedManufacturers);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateQuery)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteByIdStatement =
                        connection.prepareStatement(deleteQuery)) {
            deleteByIdStatement.setLong(1, id);
            int updatedRows = deleteByIdStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data by resultSet: " + resultSet, e);
        }
        return manufacturer;
    }
}
