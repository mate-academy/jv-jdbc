package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.DataProcessingException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtilities;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormat =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtilities.getConnection();
                PreparedStatement addManufacturer =
                        connection.prepareStatement(insertFormat,
                                Statement.RETURN_GENERATED_KEYS)) {
            addManufacturer.setString(1, manufacturer.getName());
            addManufacturer.setString(2, manufacturer.getCountry());
            addManufacturer.executeUpdate();
            ResultSet generatedKeys = addManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert format to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturer = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtilities.getConnection();
                    PreparedStatement getData = connection.prepareStatement(getManufacturer)) {
            getData.setLong(1, id);
            ResultSet resultSet = getData.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get data from DB" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturerQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturer = new ArrayList<>();
        try (Connection connection = ConnectionUtilities.getConnection();
                  Statement getAllManufacturer = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturer.executeQuery(getAllManufacturerQuery);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setCountry(country);
                manufacturer.setName(name);
                manufacturer.setId(id);
                allManufacturer.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all formats from DB", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateId = "UPDATE manufacturers"
                + " SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtilities.getConnection();
                PreparedStatement update = connection.prepareStatement(updateId,
                        Statement.RETURN_GENERATED_KEYS)) {
            update.setString(1, manufacturer.getName());
            update.setString(2, manufacturer.getCountry());
            update.setLong(3, manufacturer.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data to DB" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleted = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtilities.getConnection();
                PreparedStatement delete =
                        connection.prepareStatement(deleted, Statement.RETURN_GENERATED_KEYS)) {
            delete.setLong(1, id);
            return delete.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t deleted data from DB" + id, e);
        }
    }
}
