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
        String insertRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createStatement = connection
                        .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Create operation to DB was crashed for Manufacturer"
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = 'FALSE'";
        Manufacturer result = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                result = parseManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Get operation from DB was crashed for ID = "
                    + id, e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = 'FALSE'";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "GetAll operation from DB was crashed", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ? WHERE id = ? AND is_deleted = 'FALSE'";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setString(1, manufacturer.getName());
            prepareStatement.setLong(2, manufacturer.getId());
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Update operation from DB was crashed for Manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = 'TRUE' WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Delete operation from DB was crashed for ID = " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(
                resultSet.getString("name"),
                resultSet.getString("country"),
                resultSet.getObject("id", Long.class));
    }
}
