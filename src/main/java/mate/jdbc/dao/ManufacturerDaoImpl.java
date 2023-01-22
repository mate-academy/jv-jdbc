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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet resultSet = createManufacturerStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers "
                + "WHERE id = (?) AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection
                        .prepareStatement(getManufacturerRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (resultSet.next()) {
                Long idResult = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(idResult, name, country);
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturers = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturers
                    .executeQuery(getAllManufacturerRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get all manufacturers from manufacturers database", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = (?), "
                + "country = (?)"
                + "WHERE id = (?) AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(updateManufacturerRequest)) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.setObject(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer with id "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id " + id, e);
        }
    }
}
