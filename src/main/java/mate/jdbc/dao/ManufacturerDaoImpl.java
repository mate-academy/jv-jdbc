package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) "
                + "values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement createStatement = connection.prepareStatement(insertRequest,
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
            throw new DataProcessingException("Failed to create new manufacturer: "
                    + manufacturer.getName(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement getStatement = connection.prepareStatement(getQuery)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("No manufacturer with id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturesStatement = connection.createStatement()) {
            ResultSet resultSet = getManufacturesStatement
                    .executeQuery(getAllQuery);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update manufacturer with id: "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                           .prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete the manufacturer with id: "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        try {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get manufacturer", e);
        }
    }
}

