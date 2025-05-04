package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String sqlQuery = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Problem creating manufacturer in DB, name: "
                    + manufacturer.getName() + ", country: " + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlQuery =
                "SELECT name, country FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Manufacturer(id, resultSet.getString("name"),
                        resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Problem getting manufacturer from DB, id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String sqlQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(new Manufacturer(resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Problem getting manufacturers list from DB", e);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String sqlQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery);) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Problem creating manufacturer in DB, name: "
                    + manufacturer.getName() + ", country: " + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String sqlQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery);) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Problem deleting record from manufacturers table, id: " + id, e);
        }
    }
}
