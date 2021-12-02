package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Statement;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, manufacturer.getName());
            prepareStatement.setString(2, manufacturer.getCountry());
            prepareStatement.executeUpdate();
            ResultSet idResultSet = prepareStatement.getGeneratedKeys();
            if (idResultSet.next()) {
                return new Manufacturer(manufacturer.getName(),
                        manufacturer.getCountry(), idResultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Create operation to DB was crashed", e);
        }
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = 'FALSE'";
        Manufacturer result = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                result = new Manufacturer(
                        resultSet.getString("name"),
                        resultSet.getString("country"),
                        resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get operation from DB was crashed", e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> resultList = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = 'FALSE'";
        try (Connection connection = ConnectionUtil.getConnection()) {
            ResultSet resultSet = connection
                    .prepareStatement(query)
                    .executeQuery();
            while (resultSet.next()) {
                resultList.add(new Manufacturer(
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
        String query = "UPDATE manufacturers SET name = ? WHERE id = ? AND is_deleted = 'FALSE'";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
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
        String query = "UPDATE manufacturers SET is_deleted = 'TRUE' WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Delete operation from DB was crashed", e);
        }
    }
}
