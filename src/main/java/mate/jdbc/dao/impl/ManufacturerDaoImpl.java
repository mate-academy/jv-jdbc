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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest
                = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertPreparedStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertPreparedStatement.setString(1, manufacturer.getName());
            insertPreparedStatement.setString(2, manufacturer.getCountry());
            insertPreparedStatement.executeUpdate();
            ResultSet generatedKeys = insertPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer to DB. " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectPreparedStatement
                        = connection.prepareStatement(selectManufacturerRequest)) {
            selectPreparedStatement.setLong(1, id);
            ResultSet resultSet = selectPreparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerInstance(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id from DB."
                    + " Manufacturer id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllManufacturersRequest
                = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectPreparedStatement
                        = connection.prepareStatement(selectAllManufacturersRequest)) {
            ResultSet resultSet = selectPreparedStatement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(getManufacturerInstance(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest
                = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE is_deleted = false AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                        = connection.prepareStatement(updateManufacturerRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new SQLException("Manufacturer with id: "
                    + manufacturer.getId() + "wasn't found");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer data in DB. "
                    + "Manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateStatement
                         = connection.prepareStatement(deleteManufacturerRequest)) {
            updateStatement.setLong(1, id);
            return updateStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id from DB. "
                    + "Manufacturer id: " + id, e);
        }
    }

    private Manufacturer getManufacturerInstance(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject(1, Long.class));
            manufacturer.setName(resultSet.getObject(2, String.class));
            manufacturer.setCountry(resultSet.getObject(3, String.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer instance ", e);
        }
        return manufacturer;
    }
}
