package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "INSERT INTO manufacturers(id, name, country) VALUES(?, ?, ?)");
            prepareStatement.setLong(1, manufacturer.getId());
            prepareStatement.setString(2, manufacturer.getName());
            prepareStatement.setString(3, manufacturer.getCountry());
            return prepareStatement.executeUpdate() > 0 ? manufacturer : null;
        } catch (SQLException e) {
            throw new DataProcessingException("Create operation to DB was crashed", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer result = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = 0");
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                result = new Manufacturer(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get operation from DB was crashed", e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> resultList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            ResultSet resultSet = connection
                    .prepareStatement("SELECT * FROM manufacturers WHERE is_deleted = 0")
                    .executeQuery();
            while (resultSet.next()) {
                resultList.add(new Manufacturer(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "GetAll operation from DB was crashed", e);
        }
        return resultList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "UPDATE manufacturers SET name = ? WHERE id = ? AND is_deleted = 0");
            prepareStatement.setString(1, manufacturer.getName());
            prepareStatement.setLong(2, manufacturer.getId());
            if (prepareStatement.executeUpdate() == 0) {
                return create(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Update operation from DB was crashed", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "UPDATE manufacturers SET is_deleted = 1 WHERE id = ?");
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Delete operation from DB was crashed", e);
        }
    }
}
