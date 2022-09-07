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
        String insertManufacturerRequest
                = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKey = createManufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequestAboutManufacturer
                = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturerStatement
                         = connection.prepareStatement(getRequestAboutManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer from DB by id " + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getRequestAboutAllManufacturers
                = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getRequestAboutAllManufacturers)) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturers from DB", ex);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String getUpdateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(getUpdateManufacturerRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer
                    + " in DB", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String getDeletedRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                        = connection.prepareStatement(getDeletedRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id " + id, ex);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            return new Manufacturer(id, name, country);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create new manufacturer", ex);
        }
    }
}
