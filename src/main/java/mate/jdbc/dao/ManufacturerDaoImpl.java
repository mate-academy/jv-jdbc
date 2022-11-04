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
        String insertManufactureRequest = "INSERT INTO manufacturers(name, country) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufactureStatement = connection.prepareStatement(
                        insertManufactureRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufactureStatement.setString(1, manufacturer.getName());
            createManufactureStatement.setString(2, manufacturer.getCountry());
            createManufactureStatement.executeUpdate();
            ResultSet generatedKeys = createManufactureStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't insert manufacrurer" + manufacturer,
                    throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> getById(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufactureByIdStatement =
                        connection.prepareStatement(getByIdRequest)) {
            getManufactureByIdStatement.setLong(1, id);
            ResultSet getManufacturerSet = getManufactureByIdStatement.executeQuery();
            if (getManufacturerSet.next()) {
                Manufacturer manufacturer = parseResultSet(getManufacturerSet);
                return Optional.of(manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't get manufacturer by id" + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet getManufacturerSet = getAllManufacturerStatement.executeQuery(getAllRequest);
            while (getManufacturerSet.next()) {
                manufacturers.add(parseResultSet(getManufacturerSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            if (updateManufacturerStatement.executeUpdate() == 0) {
                throw new RuntimeException("Can't update manufacturer" + manufacturer);
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't update manufacturer" + manufacturer,
                    throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String softDeleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ? "
                + "AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement softDeleteStatement =
                        connection.prepareStatement(softDeleteRequest)) {
            softDeleteStatement.setLong(1, id);
            return softDeleteStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can't delete manufacture by id = " + id,
                    throwables);
        }
    }

    private Manufacturer parseResultSet(ResultSet getManufacturerSet) {
        try {
            Long id = getManufacturerSet.getObject("id", Long.class);
            String name = getManufacturerSet.getString("name");
            String country = getManufacturerSet.getString("country");
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer", e);
        }
    }
}
