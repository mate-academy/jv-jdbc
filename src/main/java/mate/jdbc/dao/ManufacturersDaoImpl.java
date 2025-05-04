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
import mate.jdbc.manufacturer.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert manufacturer to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT id, name, country FROM manufacturers "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? Optional.of(createManufacturer(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find manufacturer with id:" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get elements from table", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ? , country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement(query)) {
            statement.setLong(1, id);
            int executeUpdate = statement.executeUpdate();
            return executeUpdate >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete manufacturer by id:" + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long idFromSet = resultSet.getObject(1, Long.class);
            manufacturer.setId(idFromSet);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get element from manufacturers", e);
        }
    }
}
