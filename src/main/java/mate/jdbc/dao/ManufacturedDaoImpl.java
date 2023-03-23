package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.connection.ConnectionUtil;
import mate.jdbc.exception.DataException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturedDaoImpl implements ManufacturedDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createBooksStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                               Statement.RETURN_GENERATED_KEYS)) {
            createBooksStatement.setString(1, manufacturer.getName());
            createBooksStatement.setString(2, manufacturer.getCountry());
            createBooksStatement.executeUpdate();
            ResultSet generatedKeys = createBooksStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataException("Can`t create manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(convertToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataException("Can`t get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(convertToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataException("Can`t get all manufacturers from db ", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET country = ?,"
                + " name = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataException("Can`t update data for manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataException("Can`t get all books from db ", e);
        }
    }

    private Manufacturer convertToManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
