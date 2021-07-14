package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utility.ConnectionUtility;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufactures(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement getAllFormatsStatement = connection.prepareStatement(
                        insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            getAllFormatsStatement.setString(1, manufacturer.getName());
            getAllFormatsStatement.setString(2, manufacturer.getCountry());
            getAllFormatsStatement.executeUpdate();
            ResultSet generatedKeys = getAllFormatsStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer - " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest =
                "SELECT * FROM manufactures WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(selectManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturers from DB by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> getAllFormats = new ArrayList<>();
        String getAllManufacturers =
                "SELECT * FROM manufactures WHERE is_deleted = false";
        try (Connection connection = ConnectionUtility.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSetAllFormat = getAllFormatsStatement.executeQuery(getAllManufacturers);
            while (resultSetAllFormat.next()) {
                String name = resultSetAllFormat.getString("name");
                String country = resultSetAllFormat.getString("country");
                Long id = resultSetAllFormat.getObject("id", Long.class);
                Manufacturer manufacturer = parseResultSet(resultSetAllFormat);
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                getAllFormats.add(manufacturer);
            }
            return getAllFormats;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers " + e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufactures SET name = ? country = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer from DB" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufactures SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(deleteManufacturerRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer from DB" + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
