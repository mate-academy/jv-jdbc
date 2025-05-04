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
        String insertManufacturerQuery = "INSERT INTO manufacturers(name, country) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                         connection.prepareStatement(insertManufacturerQuery,
                                 Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long object = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(object);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert data to DB. Data: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerQuery = "SELECT * FROM "
                + "manufacturers WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturersStatement =
                        connection.prepareStatement(getManufacturerQuery)) {
            getManufacturersStatement.setLong(1, id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
            Manufacturer manufacturer;
            if (resultSet.next()) {
                manufacturer = createNewManufacturer(resultSet);
            } else {
                manufacturer = null;
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get data from DB. Data ID: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery("SELECT * FROM "
                        + "manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                Manufacturer manufacturer = createNewManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all data from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerQuery = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement =
                        connection.prepareStatement(updateManufacturerQuery)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setLong(3, manufacturer.getId());
            updateManufacturersStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update data to DB. Data: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerQuery = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturersStatement =
                        connection.prepareStatement(deleteManufacturerQuery)) {
            deleteManufacturersStatement.setLong(1, id);
            return deleteManufacturersStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete data to DB. Data ID: " + id, e);
        }
    }

    private Manufacturer createNewManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer(
                resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
        return manufacturer;
    }
}
