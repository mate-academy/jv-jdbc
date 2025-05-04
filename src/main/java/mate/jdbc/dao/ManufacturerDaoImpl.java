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
        String query = "INSERT INTO manufacturers(name, country)VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t write Manufacturer: "
                    + manufacturer + ", to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(convertToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get the data from DB with ID: "
                    + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data with this Manufacturer: "
                    + manufacturer.toString(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with ID: "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(convertToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all data from DB", e);
        }
        return result;
    }

    private Manufacturer convertToManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
