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
public class ManufacturerDao implements DbDao<Manufacturer> {
    private static final String CREATE_REQUEST
            = "INSERT INTO manufacturers(name, country) values (?, ?)";
    private static final String GET_REQUEST
            = "SELECT * FROM manufacturers "
            + "WHERE id = ? AND is_deleted = FALSE";
    private static final String GET_ALL_REQUEST
            = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? and is_deleted = ?";
    private static final String UPDATE_REQUEST
            = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";
    private static final String DELETE_REQUEST
            = "UPDATE manufacturers SET is_deleted = TRUE WHERE id ?";
    private static final int NAME_INDEX = 1;
    private static final int COUNTRY_INDEX = 2;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(NAME_INDEX, manufacturer.getName());
            statement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                manufacturer.setId(keys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed creation record in DB for: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_REQUEST)) {
            statement.setLong(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(mapRecord(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Searching manufacturer by id " + id + "in DB fauled", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_REQUEST)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                manufacturerList.add(mapRecord(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new RuntimeException("Failed request for all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_REQUEST)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            if (statement.getUpdateCount() > 0) {
                return manufacturer;
            }
            throw new IllegalArgumentException("Manufacturer update with id "
                    + manufacturer.getId() + " failed. Record not found");
        } catch (SQLException e) {
            throw new RuntimeException("Update record " + manufacturer + " in DB failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed deleting manufacturer from DB by id " + id, e);
        }
    }

    private Manufacturer mapRecord(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
