package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingExceptions;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectFromManufacturerStmt
                        = connection.prepareStatement(select)) {
            selectFromManufacturerStmt.setObject(1, id);
            ResultSet resultSet = selectFromManufacturerStmt.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = convertToManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Cannot get manufacturers by id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectAll = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFormatsManufacturerStmt
                        = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = getAllFormatsManufacturerStmt.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = convertToManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Сan`t get all data from manufactures", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateData = "UPDATE manufacturers SET name = ? "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStm = connection.prepareStatement(updateData)) {
            updateStm.setString(1, manufacturer.getName());
            updateStm.setString(2, manufacturer.getCountry());
            updateStm.setObject(3, manufacturer.getId());
            updateStm.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Can`t update manufactures with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteFromManufacturers
                        = connection.prepareStatement(deleteRequest)) {
            deleteFromManufacturers.setObject(1, id);
            deleteFromManufacturers.executeUpdate();
            return deleteFromManufacturers.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Can`t delete manufacturer by id = " + id, e);
        }

    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insert = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertToManufacturersStmt
                         = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            insertToManufacturersStmt.setString(1, manufacturer.getName());
            insertToManufacturersStmt.setString(2, manufacturer.getCountry());
            insertToManufacturersStmt.executeUpdate();
            ResultSet resultSet = insertToManufacturersStmt.getGeneratedKeys();
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Cannot insert manufacturer to DB"
                    + manufacturer, e);
        }
    }

    private Manufacturer convertToManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingExceptions("Can`t get data from resultSet ", e);
        }
    }
}

