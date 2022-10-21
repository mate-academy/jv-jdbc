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
    public Manufacturer create(final Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = dbConnection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer in DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(final Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = FALSE;";
        Optional<Manufacturer> result = Optional.empty();
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = dbConnection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            if (generatedKeys.next()) {
                Manufacturer manufacturer = getManufacturer(generatedKeys);
                manufacturer.setId(id);
                result = Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB: " + id, e);
        }
        return result;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String getAllManufacturersRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturersStatement
                         = dbConnection.prepareStatement(getAllManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(final Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                         = dbConnection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(final Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement
                         = dbConnection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update is_deleted "
                    + "for manufacturer in DB by id: " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer entity", e);
        }
    }
}
