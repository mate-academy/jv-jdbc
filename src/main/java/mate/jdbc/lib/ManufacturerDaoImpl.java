package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.exception.NoDataException;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturersRequest =
                "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection
                     = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement
                        = connection.prepareStatement(insertManufacturersRequest,
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
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllFromManufacturers = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        Manufacturer manufacturer;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllFromManufacturers)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery(getAllFromManufacturers);
            while (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get \"all manufacturers\" from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getFromManufacturers = "SELECT * FROM manufacturers WHERE id = ?";
        Manufacturer manufacturer;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturersStatement =
                        connection.prepareStatement(getFromManufacturers)) {
            getManufacturersStatement.setString(1, "" + id);
            ResultSet resultSet = getManufacturersStatement.executeQuery();
            manufacturer = getManufacturer(resultSet);
            if (manufacturer.isDeleted()) {
                throw new NoDataException("Can't get data from \"manufacturer\". "
                        + "String with id = " + id + " was deleted");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturersRequest = "UPDATE manufacturers SET "
                + "name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturersStatement =
                        connection.prepareStatement(updateManufacturersRequest)) {
            updateManufacturersStatement.setString(1, manufacturer.getName());
            updateManufacturersStatement.setString(2, manufacturer.getCountry());
            updateManufacturersStatement.setString(3, "" + manufacturer.getId());
            int i = updateManufacturersStatement.executeUpdate();
            if (i == 0) {
                throw new NoDataException("Can't update string with id = " + manufacturer.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute update query to "
                    + "\"manufacturers\" with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete string with id = "
                    + id + "from manufacturers", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        boolean isDeleted = resultSet.getBoolean("is_deleted");
        manufacturer.setId(manufacturerId);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setDeleted(isDeleted);
        return manufacturer;
    }
}
