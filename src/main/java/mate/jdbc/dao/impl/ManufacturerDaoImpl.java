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
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_ID = "id";

    @Override
    public Optional<Manufacturer> get(long id) {
        String selectManufacturerRequest = "SELECT * FROM manufacturers"
                + " WHERE id = ? AND is_deleted = FALSE;";
        Optional<Manufacturer> manufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(selectManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = Optional.of(retrieveManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a manufacturer by id"
                    + id + " from taxi_DB", e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectFormatsRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery(selectFormatsRequest);
            while (resultSet.next()) {
                allManufacturers.add(retrieveManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from taxi_DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatsRequest = "INSERT INTO manufacturers(name, country)"
                + " VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertFormatsRequest, Statement.RETURN_GENERATED_KEYS)) {
            setStrings(manufacturer, createManufacturerStatement);
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer to taxi_DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id " + id
                    + " in taxi_DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateRequest)) {
            setStrings(manufacturer, updateManufacturerStatement);
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer" + manufacturer
                    + " in taxi_DB", e);
        }
        return null;
    }

    private Manufacturer retrieveManufacturer(ResultSet set) throws SQLException {
        String name = set.getString(COLUMN_NAME);
        String country = set.getString(COLUMN_COUNTRY);
        Long id = set.getObject(COLUMN_ID, Long.class);
        return new Manufacturer(id, name, country);
    }

    private static void setStrings(Manufacturer manufacturer, PreparedStatement
            createManufacturerStatement) throws SQLException {
        createManufacturerStatement.setString(1, manufacturer.getName());
        createManufacturerStatement.setString(2, manufacturer.getCountry());
    }
}
