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
    private static final int NAME_INDEX = 1;
    private static final int COUNTRY_INDEX = 2;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerQuery = "INSERT INTO manufacturers(name, country) values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertManufacturerStatement
                        = connection.prepareStatement(createManufacturerQuery,
                        Statement.RETURN_GENERATED_KEYS);) {
            insertManufacturerStatement.setString(NAME_INDEX, manufacturer.getName());
            insertManufacturerStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = insertManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = false";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement selectManufacturersStatement
                        = connection.prepareStatement(selectManufacturerRequest)) {
            selectManufacturersStatement.setLong(1, id);
            ResultSet resultSet = selectManufacturersStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB", e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectAllManufacturersQuery = "SELECT * FROM manufacturers WHERE is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectAllManufacturers
                        = connection.prepareStatement(selectAllManufacturersQuery)) {
            ResultSet resultSet = selectAllManufacturers.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers list from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(updateRequest);) {
            updateManufacturerStatement.setString(NAME_INDEX, manufacturer.getName());
            updateManufacturerStatement.setString(COUNTRY_INDEX, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            return updateManufacturerStatement.executeUpdate() >= 1 ? manufacturer : null;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(deleteRequest);) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
