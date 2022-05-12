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
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("Parameter manufacturer is null");
        }
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturer = connection.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturer.setString(1, manufacturer.getName());
            insertManufacturer.setString(2, manufacturer.getCountry());
            insertManufacturer.executeUpdate();
            ResultSet generatedId = insertManufacturer.getGeneratedKeys();
            if (generatedId.next()) {
                manufacturer.setId(generatedId.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert manufacturer to database", ex);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        if (id == null) {
            throw new RuntimeException("Parameter id is null");
        }
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectManufacturer = connection.prepareStatement(query)) {
            selectManufacturer.setLong(1, id);
            ResultSet manufacturerSet = selectManufacturer.executeQuery();
            if (manufacturerSet.next()) {
                return Optional.of(parseManufacturer(manufacturerSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer wih id: " + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement selectAllManufacturers = connection.createStatement()) {
            ResultSet manufacturersSet = selectAllManufacturers.executeQuery(query);
            while (manufacturersSet.next()) {
                manufacturers.add(parseManufacturer(manufacturersSet));
            }
            return manufacturers;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturers from database", ex);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new RuntimeException("Parameter manufacturer is null");
        }
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(query)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2,manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new RuntimeException("Parameter id is null");
        }
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer with id: " + id, ex);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(1, Long.class);
        String name = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new Manufacturer(id, name, country);
    }
}
