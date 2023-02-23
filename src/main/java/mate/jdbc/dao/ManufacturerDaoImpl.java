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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int ID_COLUMN_POSITION = 1;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturer.getId() != null) {
            return update(manufacturer);
        }
        String getSqlRequest = "INSERT INTO manufacturers(country, name) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerInsertStatement
                        = connection.prepareStatement(getSqlRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            manufacturerInsertStatement.setString(1, manufacturer.getCountry());
            manufacturerInsertStatement.setString(2, manufacturer.getName());
            manufacturerInsertStatement.executeUpdate();
            ResultSet generatedKeys = manufacturerInsertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long newId = generatedKeys.getObject(ID_COLUMN_POSITION, Long.class);
                manufacturer.setId(newId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getSqlRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerGetStatement
                        = connection.prepareStatement(getSqlRequest)) {
            manufacturerGetStatement.setLong(1, id);
            manufacturerGetStatement.executeQuery();
            ResultSet resultSet = manufacturerGetStatement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(parseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get manufacturer id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getSqlRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerGetStatement
                        = connection.prepareStatement(getSqlRequest)) {
            manufacturerGetStatement.executeQuery();
            ResultSet resultSet = manufacturerGetStatement.getResultSet();
            while (resultSet.next()) {
                manufacturers.add(parseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        if (manufacturer.getId() == null) {
            return create(manufacturer);
        }
        String getSqlRequest = "UPDATE manufacturers SET country = ?, name = ? WHERE id = ?"
                + " AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerInsertStatement
                        = connection.prepareStatement(getSqlRequest)) {
            manufacturerInsertStatement.setString(1, manufacturer.getCountry());
            manufacturerInsertStatement.setString(2, manufacturer.getName());
            manufacturerInsertStatement.setLong(3, manufacturer.getId());
            manufacturerInsertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cant insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String getSqlRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerDelStatement
                        = connection.prepareStatement(getSqlRequest)) {
            manufacturerDelStatement.setLong(1, id);
            return manufacturerDelStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Cant get manufacturer id: " + id, e);
        }
    }

    private Manufacturer parseFromResultSet(ResultSet resultSet)
            throws SQLException {
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(resultSet.getObject("id", Long.class));
        newManufacturer.setCountry(resultSet.getString("country"));
        newManufacturer.setName(resultSet.getString("name"));
        return newManufacturer;
    }
}
