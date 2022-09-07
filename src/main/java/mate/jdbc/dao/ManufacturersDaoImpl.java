package mate.jdbc.dao;

import mate.jdbc.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturersDaoImpl implements ManufacturersDao {
    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllManufacturersStatement
                     = connection.prepareStatement(getAllManufacturersRequest)){
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            if (resultSet == null) {
                return new ArrayList<>();
            }
            while (resultSet.next()) {
                Manufacturer manufacturer = retrieveData(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement
                     = connection.prepareStatement(insertManufacturerRequest,
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create manufacturer" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers " +
                "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatement
                     = connection.prepareStatement(getManufacturerRequest);) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet == null) {
                return Optional.empty();
            }
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                manufacturer = retrieveData(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer with" + id, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement deleteManufacturerStatement
                = connection.prepareStatement(deleteManufacturerRequest)){
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with id " + id, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest
                = "UPDATE manufacturers SET name = ?, country = ? " +
                "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement updateManufacturerStatement
                = connection.prepareStatement(updateManufacturerRequest)){
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return Optional.ofNullable(get(manufacturer.getId())).get().orElseThrow();
        } catch (SQLException e) {
            throw new DataProcessingException
                    ("Can`t update manufacturer with new manufacturer " + manufacturer, e);
        }
    }

    private Manufacturer retrieveData(ResultSet resultSet) {
        Manufacturer manufacturer = null;
        try {
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            Long manufacturerId = resultSet.getObject("id", Long.class);
            manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
            manufacturer.setCountry(manufacturerCountry);
            manufacturer.setId(manufacturerId);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t retrieve data from ResultSet", e);
        }
        return manufacturer;
    }
}
