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
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement = connection
                         .prepareStatement(insertManufacturerRequest,
                                 Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert this manufacturer to the DB: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement
                    .executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setCountry(country);
                manufacturer.setName(name);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get this manufacturer by id: "
                    + id, throwables);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long id = resultSet.getObject("id", Long.class);
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get all manufacturers from the DB",
                    throwables);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country "
                + "= ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(1, id);
            int updatedRows = createManufacturerStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException(
                    "Can't update the status of the manufacturer with id: "
                            + id, throwables);
        }
    }
}
