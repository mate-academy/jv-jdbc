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
    @Override
    public Manufacturer create(final Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                         = dbConnection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            String name = manufacturer.getName();
            String country = manufacturer.getCountry();
            createManufacturerStatement.setString(1, name);
            createManufacturerStatement.setString(2, country);
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(final Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = false;";
        Optional<Manufacturer> result = Optional.empty();
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement
                         = dbConnection.prepareStatement(getManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            if (generatedKeys.next()) {
                String name = generatedKeys.getString("name");
                String country = generatedKeys.getString("country");
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                result = Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
        return result;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatement = dbConnection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = false");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(final Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = false";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                        = dbConnection.prepareStatement(updateRequest)) {
            Long id = manufacturer.getId();
            String name = manufacturer.getName();
            String country = manufacturer.getCountry();
            updateManufacturerStatement.setString(1, name);
            updateManufacturerStatement.setString(2, country);
            updateManufacturerStatement.setLong(3, id);
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(final Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection dbConnection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturerStatement
                        = dbConnection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
    }
}
