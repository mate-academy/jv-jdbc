package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        String insertRequest = "INSERT INTO manufacturers (name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(insertRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getName());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: " + manufacturer
                    + " to DataBase", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id + " from "
                    + " DataBase", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturerFromResultSet = getManufacturerFromResultSet(resultSet);
                allManufacturers.add(manufacturerFromResultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DataBase ", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = (?), country = (?) WHERE "
                + " is_deleted = false AND id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data with " + manufacturer
                    + "in DataBase", e);
        }
        throw new NoSuchElementException("This manufacturer: " + manufacturer
                + " doesn't exist in DataBase");
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deletedStatement = connection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deletedStatement.setLong(1, id);
            return deletedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: " + id
                    + " from DataBase ", e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setId(resultSet.getObject(1, Long.class));
        return manufacturer;
    }
}
