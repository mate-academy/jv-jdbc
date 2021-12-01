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
        String query =
                "INSERT INTO manufacturers(name, country) value(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement prepareStatement =
                             connection.prepareStatement(query,
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
        String query = "SELECT * FROM manufacturers WHERE id = ?"
                + " AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(query)) {
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
        String query = "SELECT * FROM manufacturers WHERE "
                + "is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(query)) {
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
        String query =
                "UPDATE manufacturers SET name = ?, country = ?"
                        + " WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement
                         = connection.prepareStatement(query)) {
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
        String query =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement prepareStatement =
                         connection.prepareStatement(query)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete by id " + id, e);
        }
    }

    private Manufacturer getNewManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }
}
