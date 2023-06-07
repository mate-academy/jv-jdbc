package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String request = "INSERT INTO manufacturers(name, country) "
                        + "VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(request,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert new manufacturer "
                    + manufacturer + " to db ", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String request = "SELECT * "
                        + "FROM manufacturers "
                        + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement =
                         connection.prepareStatement(request)) {
            getStatement.setObject(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(parseInfo(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id "
                    + id + " from db ", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * "
                     + "FROM manufacturers "
                     + "WHERE is_deleted = FALSE;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                allManufacturers.add(parseInfo(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from db", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers "
                     + "SET name = ?, country = ? "
                     + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(query)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setObject(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer "
                    + manufacturer + " from db ", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers "
                     + "SET is_deleted = TRUE "
                     + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete id "
                    + id + " manufacturer from db", e);
        }
    }

    private Manufacturer parseInfo(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
