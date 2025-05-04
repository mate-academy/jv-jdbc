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
        String insertManufacturerRequest;
        insertManufacturerRequest = "INSERT INTO manufacturers (name, country) values (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement statementNewManufacturer
                           = connection.prepareStatement(insertManufacturerRequest,
                           Statement.RETURN_GENERATED_KEYS);) {
            statementNewManufacturer.setString(1, manufacturer.getName());
            statementNewManufacturer.setString(2, manufacturer.getCountry());
            statementNewManufacturer.executeUpdate();
            ResultSet generatedKeys = statementNewManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create "
                    + "Manufacturer into DB. " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest;
        getManufacturerRequest = "SELECT * FROM manufacturers where id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementGetManufacturer
                        = connection.prepareStatement(getManufacturerRequest);) {
            statementGetManufacturer.setLong(1, id);
            ResultSet resultSet = statementGetManufacturer.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get "
                    + "Manufacturer from DB by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturersRequest;
        getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementGetAllManufacturers
                        = connection.prepareStatement(getAllManufacturersRequest);) {
            ResultSet resultSet = statementGetAllManufacturers.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get All"
                    + "Manufacturers from DB.", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest;
        updateManufacturerRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementUpdateManufacturer
                        = connection.prepareStatement(updateManufacturerRequest);) {
            statementUpdateManufacturer.setString(1, manufacturer.getName());
            statementUpdateManufacturer.setString(2, manufacturer.getCountry());
            statementUpdateManufacturer.setLong(3, manufacturer.getId());
            statementUpdateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update "
                    + "Manufacturer into DB. Manufacturer: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest;
        deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementDeleteManufacturer
                        = connection.prepareStatement(deleteManufacturerRequest);) {
            statementDeleteManufacturer.setLong(1, id);
            return statementDeleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete "
                    + "Manufacturer from DB by id = " + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
