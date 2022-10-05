package mate.jdbc.dao;

import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String INSERT_MANUFACTURER_REQUEST
            = "INSERT INTO manufacturer(name, country, is_deleted) VALUES(?, ?, false);";
    private static final String SELECT_MANUFACTURER_BY_ID_NOT_DELETED_REQUEST
            = "SELECT * FROM manufacturer WHERE (NOT is_deleted) AND id = ?;";
    private static final String SELECT_ALL_MANUFACTURER_NOT_DELETED_REQUEST
            = "SELECT * FROM manufacturer WHERE NOT is_deleted;";
    private static final String UPDATE_MANUFACTURER_NOT_DELETED_REQUEST
            = "UPDATE manufacturer SET name = ?, country = ? WHERE id = ?;";
    private static final String SOFT_DELETE_MANUFACTURER_REQUEST
            = "UPDATE manufacturer SET is_deleted = true WHERE id = ?;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_MANUFACTURER_REQUEST,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer in table: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_MANUFACTURER_BY_ID_NOT_DELETED_REQUEST)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return Optional.ofNullable(resultSet.next() ? parseResultSet(resultSet) : null);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find manufacturer in table by id: "
                    + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_ALL_MANUFACTURER_NOT_DELETED_REQUEST)) {
            ResultSet resultSet = statement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(parseResultSet(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select manufacturers with request: "
                    + SELECT_ALL_MANUFACTURER_NOT_DELETED_REQUEST, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_MANUFACTURER_NOT_DELETED_REQUEST)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in table: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SOFT_DELETE_MANUFACTURER_REQUEST)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer in table by id: "
                    + id, e);
        }
    }
}
