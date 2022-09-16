package mate.jdbc.dao.impl;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement = connection.prepareStatement(
                     createQuery, Statement.RETURN_GENERATED_KEYS);) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatement
                     = connection.prepareStatement(getByIdQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                Manufacturer manufacturer = Manufacturer.of(id, name, country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer from DB by id " + id, ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers.executeQuery(getAllQuery);
            while (resultSet.next()) {
                Long id =  resultSet.getObject(1, Long.class);
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                Manufacturer manufacturer = Manufacturer.of(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturers from DB", ex);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? " +
                "WHERE id = ? AND is_deleted = FALSE;";
        Optional<Manufacturer> oldManufacturer = get(manufacturer.getId());
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement
                     = connection.prepareStatement(updateQuery)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.execute();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer in DB"
                    + manufacturer.getId(), ex);
        }
        return oldManufacturer.get();
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement
                     = connection.prepareStatement(deleteQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id " + id, ex);
        }
    }
}
