package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.exeption.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery =
                "INSERT INTO manufacturers(name, country) value(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement prepareStatement =
                             connection.prepareStatement(insertQuery,
                                     Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, manufacturer.getName());
            prepareStatement.setString(2, manufacturer.getCountry());
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacture " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String getQuery = "SELECT * FROM manufacturers WHERE id = ?"
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(getQuery)) {
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getNewManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacture by id" + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers WHERE "
                + "is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(getNewManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacture from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE manufacturers SET name = ?, country = ?"
                        + " WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement
                         = connection.prepareStatement(updateQuery)) {
            prepareStatement.setString(1, manufacturer.getName());
            prepareStatement.setString(2, manufacturer.getCountry());
            prepareStatement.setLong(3, manufacturer.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacture " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(deleteQuery)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete by id " + id, e);
        }
    }

    private Manufacturer getNewManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setCountry(resultSet.getString("country"));
            manufacturer.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturer;
    }
}
