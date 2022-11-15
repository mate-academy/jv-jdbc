package mate.jdbc.dao.impl;

import java.sql.Connection;
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
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String COUNTRY_COLUMN_NAME = "country";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest
                = "INSERT INTO manufacturers(name, country) values(?, ?)";
        int parameterNameIndex = 1;
        int parameterCountryIndex = 2;
        int indexOfColumnId = 1;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createFormatStatement =
                         connection.prepareStatement(insertFormatRequest,
                                 Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(parameterNameIndex,
                    manufacturer.getName());
            createFormatStatement.setString(parameterCountryIndex,
                    manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(indexOfColumnId, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert "
                    + "manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectFormatRequest = "SELECT * "
                + "FROM manufacturers "
                + "WHERE is_deleted = false AND id = ?";
        int parameterIdIndex = 1;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(selectFormatRequest)) {
            getManufacturerStatement.setLong(parameterIdIndex, id);
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery();
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers "
                    + "from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String selectFormatRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(selectFormatRequest)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers "
                    + "from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE id = ? ";
        int parameterIdIndex = 1;
        int parameterNameIndex = 2;
        int parameterCountry = 3;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateFormatStatement =
                        connection.prepareStatement(updateRequest)) {
            updateFormatStatement
                    .setString(parameterIdIndex, manufacturer.getName());
            updateFormatStatement
                    .setString(parameterNameIndex, manufacturer.getCountry());
            updateFormatStatement
                    .setLong(parameterCountry, manufacturer.getId());
            updateFormatStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data from DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers "
                + "SET is_deleted = true "
                + "WHERE id = ?;";
        int parameterIdIndex = 1;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteFormatStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteFormatStatement.setLong(parameterIdIndex, id);
            return deleteFormatStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data from DB", e);
        }
    }

    private Manufacturer getFromResultSet(ResultSet resultSet)
            throws SQLException {
        String name = resultSet.getString(NAME_COLUMN_NAME);
        String country = resultSet.getString(COUNTRY_COLUMN_NAME);
        Long id = resultSet.getObject(ID_COLUMN_NAME, Long.class);
        return new Manufacturer(id, name, country);
    }
}
