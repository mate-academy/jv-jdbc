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
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement statement = dbConnection
                         .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't put manufacturer "
                    + manufacturer.toString() + " into DB ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseRow(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't fetch manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseRow(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't fetch all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement statement = dbConnection
                          .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't put manufacturer "
                    + manufacturer.toString() + " into DB ", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        int result;
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement statement = dbConnection
                         .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with ID " + id, e);
        }
        return result != 0;
    }

    private Manufacturer parseRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject(1, Long.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
