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
import mate.jdbc.util.ConnectionUtil;
import models.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturer(name, country, is_deleted) value(?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement = connection
                     .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT id, name, country FROM manufacturer where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSetToManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't getting manufacturer ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT id, name, country FROM manufacturer where is_deleted = ?";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllNameStatement = connection.prepareStatement(getAllQuery)) {
            getAllNameStatement.setBoolean(1, false);
            ResultSet resultSet = getAllNameStatement.executeQuery(getAllQuery);
            allManufacturers = manufacturerListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all names from DB ", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Optional<Manufacturer> getManufacturer = get(manufacturer.getId());
        if (getManufacturer.isPresent()) {
            String updateQuery = "UPDATE manufacturer SET name = ?, country = ? WHERE id = ?";
            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, manufacturer.getName());
                statement.setString(2, manufacturer.getCountry());
                statement.setLong(3, manufacturer.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Can't updating manufacturer ", e);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturer SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createDeleteStatement = connection
                     .prepareStatement(deleteQuery)) {
            createDeleteStatement.setLong(1, id);
            createDeleteStatement.executeUpdate();
            return createDeleteStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB ", e);
        }
    }

    private Manufacturer resultSetToManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getLong("id"));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get a result ", e);
        }
    }

    private List<Manufacturer> manufacturerListFromResultSet(ResultSet resultSet) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Manufacturer manufacturer = resultSetToManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get result ", e);
        }
        return manufacturers;
    }
}
