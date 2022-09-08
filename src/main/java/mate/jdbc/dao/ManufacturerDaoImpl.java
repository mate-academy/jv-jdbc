package mate.jdbc.dao;

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
        String updateQuery = "INSERT INTO manufacturers(name, country) values (?, ?);";
        try (PreparedStatement manufacturerStatement = ConnectionUtil.getConnection()
                .prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            manufacturerStatement.setString(1, manufacturer.getName());
            manufacturerStatement.setString(2, manufacturer.getCountry());
            manufacturerStatement.executeUpdate();
            ResultSet generatedKeys = manufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t create manufacturer in DB"
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String findByIdQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(findByIdQuery)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacurer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get manufacturer by id" + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturersFromDb = new ArrayList<>();
        String selectQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (PreparedStatement statement =
                     ConnectionUtil.getConnection().prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturersFromDb.add(getManufacurer(resultSet));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all manufacturer from DB", throwables);
        }
        return manufacturersFromDb;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?,"
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setObject(3, manufacturer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update manufacturer "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true "
                + "WHERE id = ? AND is_deleted = false;";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t delete manufacturer"
                    + " from DB with id " + id, throwables);
        }
    }

    private Manufacturer getManufacurer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
