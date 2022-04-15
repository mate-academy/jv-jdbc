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
        String insertRequest = "INSERT INTO manufacturers(name, country)"
                + " VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement = connection
                             .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long object = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(object);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Unable to insert manufacturer to DB, manufacturer: "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIndexRequest = "SELECT * FROM manufacturers"
                + " WHERE ID = ?";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement getManufacturerStatement =
                                connection.prepareStatement(getByIndexRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = createManufacturer(resultSet);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Unable to return object by ID " + id, throwables);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement getAllManufacturersStatement =
                                connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturer(resultSet);
                manufacturerList.add(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Unable to create list of manufacturers.",
                    throwables);
        }
        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = (?), country = (?) WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement updateManufacturerStatement =
                                connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Unable to exectue update with "
                    + manufacturer, throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement deleteManufacturerStatement =
                                connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Unable to delete object at id "
                    + id, throwables);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        manufacturer.setId(id);
        manufacturer.setCountry(country);
        manufacturer.setName(name);
        return manufacturer;
    }
}
