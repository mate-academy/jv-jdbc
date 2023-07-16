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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private Manufacturer getManufacturerObject(ResultSet resultSet) {
        Long id = null;
        String name = null;
        try {
            id = resultSet.getObject("id", Long.class);
            name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(id, name, country);
            return manufacturer;
        } catch (SQLException e) {
            String errorMessage = "Can't create Manufacturer object";
            if (id != null) {
                errorMessage += " by id " + id;
            }
            if (name != null) {
                errorMessage += " with name " + name;
            }
            throw new DataProcessingException(errorMessage, e);
        }
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant create and insert manufacturer "
                    + manufacturer.getName() + " to db", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getQuery)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturerObject(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve record with id "
                    + id + " form DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllQuery = "SELECT * FROM manufacturers where is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllDateStatement = connection.createStatement()) {
            ResultSet resultSet = getAllDateStatement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getLong("id");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all date from db", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setObject(3, manufacturer.getId());
            if (statement.executeUpdate() != 1) {
                return null;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update record for " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            int rowsAffected = deleteStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete record from the database", e);
        }
    }
}
