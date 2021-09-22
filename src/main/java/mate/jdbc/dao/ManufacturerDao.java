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
                PreparedStatement manufacturerCreateStatement = connection
                        .prepareStatement(CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            manufacturerCreateStatement.setString(NAME_INDEX, manufacturer.getName());
            manufacturerCreateStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            ResultSet keys = manufacturerCreateStatement.getGeneratedKeys();
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
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(GET_REQUEST)) {
            getManufacturerStatement.setLong(1, id);
            getManufacturerStatement.executeQuery();
            ResultSet resultSet = getManufacturerStatement.getResultSet();
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
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(GET_ALL_REQUEST)) {
            getAllManufacturersStatement.executeQuery();
            ResultSet resultSet = getAllManufacturersStatement.getResultSet();
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
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(GET_ALL_REQUEST)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.getUpdateCount() > 0) {
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
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(DELETE_REQUEST)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
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
