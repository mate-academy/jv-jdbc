package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public List<Manufacturer> getAll() {
        String selectAllFromRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getAllManufacturersStatement =
                             connection.prepareStatement(selectAllFromRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertFromRequest = "INSERT INTO manufacturers(name, country) VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement =
                             connection.prepareStatement(insertFromRequest,
                                    Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long manufacturerId = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(manufacturerId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer from DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement = connection
                            .prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement = connection
                            .prepareStatement(getByIdRequest)) {
            createManufacturerStatement.setObject(1, id);
            ResultSet resultSet = createManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id " + id, e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement createManufacturerStatement = connection
                          .prepareStatement(updateRequest)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setObject(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String manufacturerName = resultSet.getString("name");
        String manufacturerCountry = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        manufacturer.setName(manufacturerName);
        manufacturer.setCountry(manufacturerCountry);
        return manufacturer;
    }
}
