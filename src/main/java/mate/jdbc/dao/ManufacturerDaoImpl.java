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
        String insert = "INSERT INTO manufacturers(name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertToManufacturersStmt
                        = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            insertToManufacturersStmt.setString(1, manufacturer.getName());
            insertToManufacturersStmt.setString(2, manufacturer.getCountry());
            insertToManufacturersStmt.executeUpdate();
            ResultSet resultSet = insertToManufacturersStmt.getGeneratedKeys();
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot insert manufacturer" + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectFromManufacturerStmt
                        = connection.prepareStatement(select)) {
            selectFromManufacturerStmt.setObject(1, id);
            ResultSet resultSet = selectFromManufacturerStmt.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = convertToManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get manufacturer by id = "
            + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectAll = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFromManufacturersStmt
                        = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = getAllFromManufacturersStmt.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = convertToManufacturer(resultSet);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all from manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, "
                + "country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStmt = connection.prepareStatement(updateRequest)) {
            updateStmt.setString(1, manufacturer.getName());
            updateStmt.setString(2, manufacturer.getCountry());
            updateStmt.setObject(3, manufacturer.getId());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update manufacturer by id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStmt = connection.prepareStatement(deleteRequest)) {
            deleteStmt.setObject(1, id);
            deleteStmt.executeUpdate();
            return deleteStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete manufacturer by id =" + id, e);
        }
    }

    private Manufacturer convertToManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(id);
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get data from resultSet", e);
        }
    }
}
