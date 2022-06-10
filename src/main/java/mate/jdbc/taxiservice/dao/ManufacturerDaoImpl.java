package mate.jdbc.taxiservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.taxiservice.exceptions.DataProcessingException;
import mate.jdbc.taxiservice.model.Manufacturer;
import mate.jdbc.taxiservice.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest =
                "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet result = createManufacturerStatement.getGeneratedKeys();
            if (result.next()) {
                manufacturer.setId(result.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerByIdRequest =
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectManufacturerByIdStatement =
                        connection.prepareStatement(selectManufacturerByIdRequest)) {
            selectManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet = selectManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectManufacturerByIdRequest =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectManufacturerByIdStatement =
                        connection.prepareStatement(selectManufacturerByIdRequest)) {
            ResultSet resultSet = selectManufacturerByIdStatement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject(1, Long.class));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setCountry(resultSet.getString(3));
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all Manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data from Manufacturers in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers "
                + "SET is_deleted = true WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data from Manufacturers in DB", e);
        }
    }
}
