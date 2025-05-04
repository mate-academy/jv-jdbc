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
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFormatRequest = "INSERT manufacturers(name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(
                             insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> manufacturerOptional = Optional.empty();
        String getManufacturerByIdRequest =
                "SELECT * FROM manufacturers WHERE id = (?) AND is_deleted = 'false'";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerByIdStatement =
                        connection.prepareStatement(getManufacturerByIdRequest)) {
            getManufacturerByIdStatement.setObject(1, id);
            ResultSet currentManufacturer = getManufacturerByIdStatement.executeQuery();
            if (currentManufacturer.next()) {
                manufacturerOptional = Optional.of(retrieveData(currentManufacturer));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer by id " + id, e);
        }
        return manufacturerOptional;
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturersRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = 'false'";
        List<Manufacturer> manufacturersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement =
                        connection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet allManufacturersResultSet =
                    getAllManufacturersStatement.executeQuery();
            while (allManufacturersResultSet.next()) {
                manufacturersList.add(retrieveData(allManufacturersResultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = (?), country = (?)"
                + " WHERE id = (?) AND is_deleted = 'false'";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setObject(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t update manufacturer by id " + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = 'true' WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteRequest)) {
            deleteStatement.setObject(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer by id " + id, e);
        }
    }

    private Manufacturer retrieveData(ResultSet manufacturer) {
        try {
            return new Manufacturer(manufacturer.getObject(1, Long.class),
                    manufacturer.getString("name"),
                    manufacturer.getString("country"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t retrieve data from " + manufacturer, e);
        }
    }
}
