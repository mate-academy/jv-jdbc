package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String CREATE_QUERY = "INSERT INTO manufacturers (name, country) "
            + "VALUES (?, ?)";
    private static final String GET_QUERY = "SELECT * FROM manufacturers WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM manufacturers";
    private static final String UPDATE_QUERY = "UPDATE manufacturers SET name = ?, "
            + "country = ? WHERE id = ?";
    private static final String DELETE_QUERY = "UPDATE manufacturers SET is_deleted = 1 "
            + "WHERE id = ?";

    private final Connection connection;

    public ManufacturerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create manufacturer.", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = createManufacturerFromResultSet(resultSet);
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get manufacturer.", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturerFromResultSet(resultSet);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get manufacturers.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new DataProcessingException("Failed to update manufacturer. "
                        + "Manufacturer not found.");
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update manufacturer.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete manufacturer.", e);
        }
    }

    private Manufacturer createManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getLong("id"));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
