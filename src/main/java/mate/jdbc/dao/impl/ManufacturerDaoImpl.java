package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.jdbc.DataProcessingException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertStatement = "INSERT INTO manufacturers (name,country) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertStatement,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException ("Can't insert manufacturer to DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerByIdQuery =
                "SELECT * FROM manufacturers WHERE id = (?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection
                        .prepareStatement(getManufacturerByIdQuery,
                                PreparedStatement.RETURN_GENERATED_KEYS)) {
            getByIdStatement.setLong(1, id);
            ResultSet genetatedKeys = getByIdStatement.executeQuery();
            if (genetatedKeys.next()) {
                return Optional.of(getEntityFromResultSet(genetatedKeys));
            }
        } catch (SQLException e) {
            throw new DataProcessingException ("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllQuery)) {
            ResultSet generatedKeys = getAllManufacturersStatement.executeQuery();
            while (generatedKeys.next()) {
                manufacturers.add(getEntityFromResultSet(generatedKeys));
            }
        } catch (SQLException e) {
            throw new DataProcessingException ("Can't get all from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturedQuery = "UPDATE manufacturers SET name = (?), country = (?)"
                + " WHERE id = (?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement =
                        connection.prepareStatement(updateManufacturedQuery)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException ("Can't update " + manufacturer + " in DB", e);
        }
        return get(manufacturer.getId()).orElse(new Manufacturer());
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerQuery =
                "UPDATE manufacturers SET is_deleted = true WHERE id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete from DB by id: " + id, e);
        }
    }

    private Manufacturer getEntityFromResultSet(ResultSet resultSet) {
        try {
            return new Manufacturer(resultSet.getLong(1),
                    resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            throw new RuntimeException("Can't create Entity from ResultSet", e);
        }
    }
}
