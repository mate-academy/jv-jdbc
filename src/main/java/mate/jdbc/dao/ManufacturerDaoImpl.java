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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String manufacturerName = manufacturer.getName();
        String manufacturerCountry = manufacturer.getCountry();
        String insertFormatRequest = "INSERT manufacturers(name, country) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(
                             insertFormatRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturerName);
            createManufacturerStatement.setString(2, manufacturerCountry);
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert manufacturer " + manufacturer.getName(), e);
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
                if (currentManufacturer.getString("is_deleted").equals("true")) {
                    return manufacturerOptional;
                }
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(currentManufacturer.getString("name"));
                manufacturer.setCountry(currentManufacturer.getString("country"));
                manufacturerOptional = Optional.of(manufacturer);
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
                Manufacturer currentManufacturer = new Manufacturer();
                currentManufacturer.setId(allManufacturersResultSet.getObject(1, Long.class));
                currentManufacturer.setName(allManufacturersResultSet.getString("name"));
                currentManufacturer.setCountry(allManufacturersResultSet.getString("country"));
                manufacturersList.add(currentManufacturer);
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
}
