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
        String query = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(query,
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? and is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement =
                        connection.prepareStatement(query)) {
            getByIdStatement.setLong(1, id);
            ResultSet generatedKeys = getByIdStatement
                    .executeQuery();
            if (generatedKeys.next()) {
                return Optional.of(parseResultSet(generatedKeys));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers where is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        Manufacturer manufacturer;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement =
                        connection.prepareStatement(query)) {
            ResultSet resultSet = getAllStatement
                    .executeQuery();
            while (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from db", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ?"
                + " where id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(query)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id " + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        String manufacturerName = resultSet.getString("name");
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String manufacturedCountry = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        manufacturer.setName(manufacturerName);
        manufacturer.setCountry(manufacturedCountry);
        return manufacturer;
    }
}
