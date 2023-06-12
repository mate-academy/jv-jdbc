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
    private static final String INSERT_MANUFACTURER_SQL =
            "INSERT INTO manufacturers(name, country) VALUES(?,?);";
    private static final String SELECT_ALL_MANUFACTURERS_SQL =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String SELECT_MANUFACTURER_BY_ID_SQL =
            "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
    private static final String UPDATE_MANUFACTURER_SQL =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";
    private static final String DELETE_MANUFACTURER_SQL =
            "UPDATE manufacturers SET is_deleted = 1 WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerStatement =
                         connection.prepareStatement(
                                 INSERT_MANUFACTURER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(1, manufacturer.getName());
            insertManufacturerStatement.setString(2, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Can't insert manufacturer %s into DB", manufacturer), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement selectManufacturerByIdStatement =
                            connection.prepareStatement(SELECT_MANUFACTURER_BY_ID_SQL)) {
            selectManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = selectManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturerFromResultSetRow(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectAllManufacturersStatement =
                        connection.prepareStatement(SELECT_ALL_MANUFACTURERS_SQL)) {
            ResultSet resultSet = selectAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(createManufacturerFromResultSetRow(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    private Manufacturer createManufacturerFromResultSetRow(ResultSet resultSet)
            throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(UPDATE_MANUFACTURER_SQL)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(DELETE_MANUFACTURER_SQL)) {
            deleteManufacturerStatement.setLong(1, id);
            int rowsAffected = deleteManufacturerStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, e);
        }
    }
}
