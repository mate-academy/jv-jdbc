package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtils;

@Dao
public class DefaultManufacturerDao implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers (name, country) "
                + "VALUES (?, ?)";

        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();

            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create manufacturer=" + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * "
                + "FROM manufacturers "
                + "WHERE id = ? AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getManufacturerByIdStatement =
                        connection.prepareStatement(query)) {
            getManufacturerByIdStatement.setObject(1, id, JDBCType.BIGINT);
            ResultSet resultSet = getManufacturerByIdStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(extractManufacturer(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get manufacturer by id=" + id, e);
        }
    }

    private Manufacturer extractManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * "
                + "FROM manufacturers "
                + "WHERE is_deleted = FALSE";

        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(query)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(extractManufacturer(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";

        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(query)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, manufacturer.getId(), JDBCType.BIGINT);
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update manufacturer=" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers "
                + "SET is_deleted = TRUE "
                + "WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement deleteManufacturerByIdStatement =
                        connection.prepareStatement(query)) {
            deleteManufacturerByIdStatement.setObject(1, id, JDBCType.BIGINT);
            int rowsAffected = deleteManufacturerByIdStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete manufacturer by id=" + id, e);
        }
    }
}
