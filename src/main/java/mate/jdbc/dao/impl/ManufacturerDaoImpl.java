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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?,?)";
        Manufacturer savedManufacturer = new Manufacturer(manufacturer);
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerPrepareStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerPrepareStatement.setString(1, manufacturer.getName());
            insertManufacturerPrepareStatement.setString(2, manufacturer.getCountry());
            insertManufacturerPrepareStatement.executeUpdate();
            ResultSet generatedIds = insertManufacturerPrepareStatement.getGeneratedKeys();
            if (generatedIds.next()) {
                Long savedId = generatedIds.getObject(1, Long.class);
                savedManufacturer.setId(savedId);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Cannot insert manufacturer: "
                    + manufacturer + " to DB", throwables);
        }
        return savedManufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectByIdRequest = "SELECT * FROM `manufacturers` WHERE (`id` = ?);";
        Manufacturer selectedManufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerPrepareStatement = connection
                        .prepareStatement(selectByIdRequest)) {
            updateManufacturerPrepareStatement.setString(1, String.valueOf(id));
            ResultSet selectedById = updateManufacturerPrepareStatement.executeQuery();
            if (selectedById.next()) {
                String name = selectedById.getObject("name", String.class);
                String country = selectedById.getObject("country", String.class);
                selectedManufacturer = new Manufacturer(id, name, country);

            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Cannot select manufacturer by ID: "
                    + id + " from DB", throwables);
        }
        return Optional.ofNullable(selectedManufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement allManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = allManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE (`is_deleted` = false)");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getObject("name", String.class);
                String country = resultSet.getObject("country", String.class);
                manufacturers.add(new Manufacturer(id, name, country));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Cannot get all manufacturers from DB",
                    throwables);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest;
        updateRequest = "UPDATE `manufacturers` SET `name` = ?, `country` = ? WHERE (`id` = ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerPrepareStatement = connection
                        .prepareStatement(updateRequest, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerPrepareStatement.setString(1, manufacturer.getName());
            updateManufacturerPrepareStatement.setString(2, manufacturer.getCountry());
            updateManufacturerPrepareStatement.setString(3, String.valueOf(manufacturer.getId()));
            updateManufacturerPrepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Cannot update manufacturer: "
                    + manufacturer + " to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String softDeleteRequest;
        softDeleteRequest = "UPDATE `manufacturers` SET `is_deleted` = true WHERE (`id` = ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerPrepareStatement = connection
                        .prepareStatement(softDeleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerPrepareStatement.setString(1, String.valueOf(id));
            return deleteManufacturerPrepareStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Cannot delete manufacturer by ID: "
                    + id + " from DB", throwables);
        }

    }
}
