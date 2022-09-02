package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String insertManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturersStatement =
                         connection.prepareStatement(insertManufacturerRequest,
                             PreparedStatement.RETURN_GENERATED_KEYS);) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement =
                         connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = getDataFromResultSet(id, manufacturer, resultSet);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't manufacturer from DB by id " + id, e);
        }
        if (manufacturer.getName() == null && manufacturer.getCountry() == null) {
            return Optional.empty();
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        Manufacturer manufacturer = new Manufacturer();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturersStatement =
                         connection.prepareStatement(getAllManufacturerRequest);
                 ResultSet resultSet = getAllManufacturersStatement
                         .executeQuery(getAllManufacturerRequest)) {
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                manufacturer = getDataFromResultSet(id, manufacturer, resultSet);
                allManufacturer.add(manufacturer.clone());
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Optional<Manufacturer> optional = get(manufacturer.getId());
        if (optional.isEmpty()) {
            throw new RuntimeException("Can't update manufacturer with this id. ID: "
                    + manufacturer.getId());
        }
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? "
                        + "where id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturersStatement =
                         connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't update manufacturer with this id. ID:"
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturersStatement =
                         connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() > 0;
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB. id = "
                    + id, e);
        }
    }

    private static Manufacturer getDataFromResultSet(
            Long id, Manufacturer manufacturer, ResultSet resultSet) throws SQLException {
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        manufacturer.setId(id);
        manufacturer.setName(manufacturerName);
        manufacturer.setCountry(manufacturerCountry);
        return manufacturer;
    }
}
