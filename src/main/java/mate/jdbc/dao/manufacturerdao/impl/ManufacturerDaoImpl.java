package mate.jdbc.dao.manufacturerdao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.customexception.DataProcessingException;
import mate.jdbc.dao.manufacturerdao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer "
                    + manufacturer.getName(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> manufacturer = Optional.of(new Manufacturer(null, null));
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                           .prepareStatement("SELECT * FROM manufacturers WHERE id = " + id)) {
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                manufacturer = Optional.of(new Manufacturer(manufacturerName, manufacturerCountry));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id " + id, e);
        }
        manufacturer.get().setId(id);
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement("SELECT * FROM manufacturers WHERE is_deleted = FALSE")) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                String manufacturerName = resultSet.getString("name");
                String manufacturerCountry = resultSet.getString("country");
                Long id = resultSet.getObject("id",Long.class);
                Manufacturer manufacturer = new Manufacturer(manufacturerName,manufacturerCountry);
                manufacturer.setId(id);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name=?,country=? WHERE id= " + manufacturer.getId();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1,manufacturer.getName());
            updateManufacturerStatement.setString(2,manufacturer.getCountry());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                           .prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1,id);
            int updatedRows = deleteManufacturerStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }
}
