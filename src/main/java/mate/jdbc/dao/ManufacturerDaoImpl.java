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
        String getRequest =
                "INSERT INTO manufacturers(manufacturer_name, manufacturer_country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                         connection.prepareStatement(getRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new manufacturer with id = "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturer =
                         connection.prepareStatement(updateRequest)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete manufacturer with id = " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest =
                "SELECT * FROM manufacturers WHERE manufacturer_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturer =
                         connection.prepareStatement(getRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Record with id = " + id
                        + " does not exist or deleted!");
            } else {
                Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
                String manufacturerName = resultSet.getString("manufacturer_name");
                String manufacturerCountry =
                        resultSet.getString("manufacturer_country");
                return Optional.of(
                        new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get manufacturer with id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturersList = new ArrayList<>();
        try (Connection connection =
                     ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet =
                    getAllManufacturersStatement.executeQuery(getRequest);
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
                String manufacturerName = resultSet.getString("manufacturer_name");
                String manufacturerCountry =
                        resultSet.getString("manufacturer_country");
                Manufacturer manufacturer =
                        new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
                allManufacturersList.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all manufacturers from DB", e);
        }
        return allManufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_country = ?"
                        + "WHERE manufacturer_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                         connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() > 0) {
                return manufacturer;
            } else {
                throw new RuntimeException("Can`t update record with id = " + manufacturer.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new manufacturer", e);
        }
    }
}
