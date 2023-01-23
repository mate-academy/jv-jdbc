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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacturer in DB " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturer
                         = connection.prepareStatement(selectRequest)) {
            getAllManufacturer.setLong(1, id);
            ResultSet resultSet = getAllManufacturer.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer from DB by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getALlRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturer = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturer
                    .executeQuery(getALlRequest);
            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement = connection
                         .prepareStatement(updateRequest)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setLong(3, manufacturer.getId());
            if (createManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + manufacturer, e);
        }
        throw new NoSuchElementException("Can't find " + manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement = connection
                         .prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer from DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
