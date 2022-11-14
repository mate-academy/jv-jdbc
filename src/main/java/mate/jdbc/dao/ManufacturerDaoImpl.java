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
    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturers = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllManufacturers.executeQuery(query);
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                manufacturer = createManufacturerResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name,country) values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement =
                     connection.prepareStatement(query,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer" +
                    manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteManufacturerStatement =
                     connection.prepareStatement(query)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to db" + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE SET name =?, country = ? "
                + "where id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(query)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer to db" +
                    manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id = ? AND" +
                " is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(query)) {
            updateManufacturerStatement.setLong(1, id);
            ResultSet resultSet = updateManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = createManufacturerResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer for id" +
                    id, e);
        }
    }

    private Manufacturer createManufacturerResultSet(ResultSet resultSet)
            throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject("id", Long.class);
        return  new Manufacturer(id, name, country);
    }
}
