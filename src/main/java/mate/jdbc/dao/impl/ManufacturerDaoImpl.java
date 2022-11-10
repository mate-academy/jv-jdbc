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
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int ID_INDEX_COLUMN = 1;
    private static final int NAME_INDEX_COLUMN = 2;
    private static final int COUNTRY_INDEX_COLUMN = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, manufacturer.getName());
            createManufacturer.setString(2, manufacturer.getCountry());
            createManufacturer.executeUpdate();
            ResultSet generatedKeys = createManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create Manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> manufacturer = Optional.empty();
        String getByIdQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer =
                        connection.prepareStatement(getByIdQuery)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Manufacturer from DB with id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers.executeQuery(getAllQuery);
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet).get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateQueryStatement =
                        connection.prepareStatement(updateQuery)) {
            updateQueryStatement.setString(1, manufacturer.getName());
            updateQueryStatement.setString(2, manufacturer.getCountry());
            updateQueryStatement.setObject(3, manufacturer.getId());
            updateQueryStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t update data of Manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(deleteQuery)) {
            deleteStatement.setObject(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t delete Manufacturer from DB with id = " + id, e);
        }
    }

    private Optional<Manufacturer> getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject(ID_INDEX_COLUMN, Long.class));
        manufacturer.setName(resultSet.getString(NAME_INDEX_COLUMN));
        manufacturer.setCountry(resultSet.getString(COUNTRY_INDEX_COLUMN));
        return Optional.of(manufacturer);
    }
}
