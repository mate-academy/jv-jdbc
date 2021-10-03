package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.custom_exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long manufacturerId = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(manufacturerId);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t insert manufacturer to DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try(Connection connection = ConnectionUtil.getConnection();
        PreparedStatement getManufacturerStatement = connection.prepareStatement("SELECT * FROM manufacturers where id = ? AND is_deleted = false")) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("id", Long.class);
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                manufacturer = new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get manufacturer from DB", throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();  ///!!
        try(Connection connection = ConnectionUtil.getConnection();
            Statement getAllManufacturesStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturesStatement.executeQuery("SELECT * FROM manufacturers where is_deleted = false");
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("id", Long.class);
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(manufacturerId);
                manufacturer.setName(manufacturerName);
                manufacturer.setCountry(manufacturerCountry);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Long manufacturerId = manufacturer.getId();
        String updateManufacturerRequest = "UPDATE manufacturers SET id = ?, name = ?, country = ? WHERE id = ? AND is_deleted = false";
        try(Connection connection = ConnectionUtil.getConnection();
        PreparedStatement updateManufacturerStatement = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setLong(1,manufacturerId);
            updateManufacturerStatement.setString(2, manufacturer.getName());
            updateManufacturerStatement.setString(3, manufacturer.getCountry());
            updateManufacturerStatement.setLong(4, manufacturerId);
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update manufacturer in DB", throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t delete manufacturer from DB", throwables);
        }
    }
}
