package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DaoException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDao implements mate.jdbc.dao.Dao<Manufacturer> {
    private Manufacturer manufacturer = new Manufacturer();

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertQuery = "INSERT INTO manufacturers(name, country) "
                + "VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(
                        insertQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, manufacturer.getName());
            insertStatement.setString(2, manufacturer.getCountry());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new DaoException("Can't insert manufacturer to db", throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectQuery = "SELECT * FROM manufacturers WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getQuery = connection.prepareStatement(selectQuery,
                    Statement.RETURN_GENERATED_KEYS);
            getQuery.setLong(1, id);
            getQuery.executeQuery();
            ResultSet resultSet = getQuery.getGeneratedKeys();
            return Optional.ofNullable(parseResultSet(resultSet));
        } catch (SQLException throwables) {
            throw new DaoException("Can't get all formats from db", throwables);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement getAllQuery = connection.createStatement();
            ResultSet resultSet = getAllQuery.executeQuery(
                    "SELECT * FROM manufacturers WHERE is_deleted = false;");
            while (resultSet.next()) {
                result.add(new Manufacturer(
                        resultSet.getLong(1),
                        resultSet.getString("name"),
                        resultSet.getString("country")
                ));
            }
            return result;
        } catch (SQLException throwables) {
            throw new DaoException("Can't get all formats from db", throwables);
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?,"
                + " country = ? WHERE id = ? && is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                            connection.prepareStatement(updateQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return Optional.ofNullable(parseResultSet(resultSet));
        } catch (SQLException throwables) {
            throw new DaoException(
                    "Can't update element here: " + manufacturer.getId() + " index", throwables);
        }
    }

    @Override
    public boolean delete(Long columnId) {
        String updateQuary = "UPDATE manufacturers"
                + " SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(updateQuary,
                             Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, columnId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_deleted");
            }
            return true;
        } catch (SQLException throwables) {
            throw new DaoException("Can't delete manufacturer at id: "
                    + columnId, throwables);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Manufacturer(resultSet.getLong(1),
                    resultSet.getString("name"),
                    resultSet.getString("country"));
        }
        return null;
    }
}
