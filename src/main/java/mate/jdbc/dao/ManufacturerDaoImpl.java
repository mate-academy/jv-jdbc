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
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufactures(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufaturer =
                         connection.prepareStatement(insertManufacturerRequest,
                                 Statement.RETURN_GENERATED_KEYS);) {
            createManufaturer.setString(1, manufacturer.getName());
            createManufaturer.setString(2, manufacturer.getCountry());
            createManufaturer.executeUpdate();
            ResultSet generatedKeys = createManufaturer.getGeneratedKeys();
            if (generatedKeys.next()) {
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
        String getManufacturerRequest =
                "SELECT * FROM manufactures WHERE is_deleted = false AND id = ?;";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufaturer =
                         connection.prepareStatement(getManufacturerRequest);) {
            getManufaturer.setLong(1, id);
            ResultSet resultSet = getManufaturer.executeQuery();
            while (resultSet.next()) {
                manufacturer = manufacturerObjectCreater(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by ID: " + id, e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        String getManufacturerRequest = "SELECT * FROM manufactures WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement getAllManufatures =
                        connection.prepareStatement(getManufacturerRequest);) {
            ResultSet resultSet = getAllManufatures.executeQuery();
            while (resultSet.next()) {
                allManufacturer.add(manufacturerObjectCreater(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufactures from DB", e);
        }
        return allManufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufactures SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufaturer =
                         connection.prepareStatement(updateManufacturerRequest);) {
            createManufaturer.setString(1, manufacturer.getName());
            createManufaturer.setString(2, manufacturer.getCountry());
            createManufaturer.setLong(3, manufacturer.getId());
            createManufaturer.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String updateManufacturerRequest =
                "UPDATE manufactures SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufaturer =
                        connection.prepareStatement(updateManufacturerRequest);) {
            createManufaturer.setLong(1, id);
            return createManufaturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't deleted manufacturer with ID: " + id, e);
        }
    }

    private Manufacturer manufacturerObjectCreater(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
