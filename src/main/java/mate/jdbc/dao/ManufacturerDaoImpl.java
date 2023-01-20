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
                PreparedStatement createManufacturersStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectAllManufacturers = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturers = connection
                        .prepareStatement(selectAllManufacturers)) {
            getManufacturers.setObject(1, id);
            ResultSet resultSet = getManufacturers.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(new Manufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAllManufacturers = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers = connection
                        .prepareStatement(selectAllManufacturers)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturers = connection
                        .prepareStatement(updateManufacturerRequest)) {
            updateManufacturers.setString(1, manufacturer.getName());
            updateManufacturers.setString(2, manufacturer.getCountry());
            updateManufacturers.setObject(3, manufacturer.getId());
            updateManufacturers.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(deleteRequest)) {
            createManufacturerStatement.setObject(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: " + id, e);
        }
    }
}
