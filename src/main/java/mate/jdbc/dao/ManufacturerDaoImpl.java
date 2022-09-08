package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = new Manufacturer();
        String sql = "INSERT INTO manufacturer(name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerStatement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(1, manufacturer.getName());
            insertManufacturerStatement.setString(2, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                updatedManufacturer.setId(generatedKeys.getObject(1, Long.class));
                updatedManufacturer.setName(manufacturer.getName());
                updatedManufacturer.setCountry(manufacturer.getCountry());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert data to db. Prams: manufacturer = "
                    + manufacturer, e);
        }
        return updatedManufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String sql = "SELECT * FROM manufacturer WHERE id = ? and isdeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection.prepareStatement(sql)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject("id", Long.class));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get data from table manufacturer by id = "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturedStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturedStatement
                    .executeQuery("SELECT * FROM manufacturer WHERE isdeleted = false");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all data from table manufacturer", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = new Manufacturer();
        String sql = "UPDATE manufacturer SET name = ?, country = ? WHERE id = ? and "
                + " isdeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = updateManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                updatedManufacturer.setId(generatedKeys.getObject("id", Long.class));
                updatedManufacturer.setName(generatedKeys.getString("name"));
                updatedManufacturer.setCountry(generatedKeys.getString("country"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update value in db. Param: manufacturer = "
                    + manufacturer.toString(), e);
        }
        return updatedManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "UPDATE manufacturer SET isdeleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection.prepareStatement(sql)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete value in db. Param: id = " + id, e);
        }
    }
}
