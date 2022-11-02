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
    private static final int ID_COLUMN = 1;
    private static final int NAME_COLUMN = 2;
    private static final int COUNTRY_COLUMN = 3;
    private static final int IS_DELETED_COLUMN = 4;
    private static final String CREATE_MANUFACTURER_REQUEST =
            "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
    private static final String GET_MANUFACTURER_REQUEST =
            "SELECT * FROM manufacturers WHERE id = ?;";
    private static final String GET_ALL_MANUFACTURERS_REQUEST =
            "SELECT * FROM manufacturers;";
    private static final String UPDATE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
    private static final String DELETE_MANUFACTURER_REQUEST =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(CREATE_MANUFACTURER_REQUEST,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_COLUMN, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer='"
                    + manufacturer + "' to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> optionalManufacturer = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(GET_MANUFACTURER_REQUEST)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                optionalManufacturer = getEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id=" + id, e);
        }
        return optionalManufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(GET_ALL_MANUFACTURERS_REQUEST)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Optional<Manufacturer> optionalManufacturer = getEntity(resultSet);
                optionalManufacturer.ifPresent(manufacturers::add);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(UPDATE_MANUFACTURER_REQUEST)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (get(manufacturer.getId()).isPresent()) {
                updateManufacturerStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer='"
                    + manufacturer + "' in DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(DELETE_MANUFACTURER_REQUEST)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id=" + id, e);
        }
    }

    private Optional<Manufacturer> getEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN, Long.class);
        String name = resultSet.getString(NAME_COLUMN);
        String country = resultSet.getString(COUNTRY_COLUMN);
        boolean isDeleted = resultSet.getBoolean(IS_DELETED_COLUMN);
        if (!isDeleted) {
            return Optional.of(new Manufacturer(id, name, country));
        }
        return Optional.empty();
    }
}
