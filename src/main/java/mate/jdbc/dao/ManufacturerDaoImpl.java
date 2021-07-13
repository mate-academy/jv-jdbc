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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utility.ConnectionUtility;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO init_db(name, country) values(?, ?);";
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
            throw new RuntimeException("Can't create statement " + e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> getIdManufacturer = Optional.empty();
        String getById = "SELECT  * from init_db WHERE isDeleted = false";
        List<Manufacturer> getAllFormats = new ArrayList<>();
        try (Connection connection = ConnectionUtility.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSetAllFormat = getAllFormatsStatement.executeQuery(getById);
            while (resultSetAllFormat.next()) {
                Long currentId = resultSetAllFormat.getObject("id", Long.class);
                if (id.equals(currentId)) {
                    String name = resultSetAllFormat.getString("Name");
                    String country = resultSetAllFormat.getString("Country");
                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setId(id);
                    manufacturer.setName(name);
                    manufacturer.setCountry(country);
                    getIdManufacturer = Optional.of(manufacturer);
                }
            }
            return getIdManufacturer;

        } catch (SQLException e) {
            throw new RuntimeException("Can't create statement " + e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> getAllFormats = new ArrayList<>();
        String getAllManufacturers =
                "SELECT  * from init_db WHERE isDeleted = false";
        try (Connection connection = ConnectionUtility.getConnection();
                Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSetAllFormat = getAllFormatsStatement.executeQuery(getAllManufacturers);
            while (resultSetAllFormat.next()) {
                String name = resultSetAllFormat.getString("Name");
                String country = resultSetAllFormat.getString("Country");
                Long id = resultSetAllFormat.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                getAllFormats.add(manufacturer);
            }
            return getAllFormats;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create statement " + e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE init_db SET name = ? WHERE id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setObject(2, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer from DB" + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE init_db SET isDeleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(deleteManufacturerRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer from DB" + id, e);
        }
    }
}
