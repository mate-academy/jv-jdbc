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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeLargeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequestById = "SELECT * FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequestById)) {
            getManufacturerStatement.setObject(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(extractManufacturerFromResultSet(resultSet));
            } else {
                throw new DataProcessingException("Manufacturer with ID " + id + " not found.",
                        new RuntimeException("Manufacturer not found in DB."));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers = connection
                        .prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(getAllManufacturerRequest);
            while (resultSet.next()) {
                allManufacturers.add(extractManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB.", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long updatedId = manufacturer.getId();
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, updatedId);
            int rowsUpdateResult = updateManufacturerStatement.executeUpdate();
            if (rowsUpdateResult == 0) {
                throw new DataProcessingException("Manufacturer with ID " + updatedId
                        + " not found.", new RuntimeException("Manufacturer not found in DB"));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB " + updatedId, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET "
                + "is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            int rowsDeleteResult = deleteManufacturerStatement.executeUpdate();
            if (rowsDeleteResult == 0) {
                throw new DataProcessingException("Manufacturer with ID " + id + " not found.",
                        new RuntimeException("Manufacturer not found in DB"));
            }
            return rowsDeleteResult > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB" + id, e);
        }
    }

    private Manufacturer extractManufacturerFromResultSet(ResultSet resultSet)
            throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
