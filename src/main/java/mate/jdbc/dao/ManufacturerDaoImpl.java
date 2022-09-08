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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNewManufacturer = connection
                        .prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturer.setString(1, manufacturer.getName());
            createNewManufacturer.setString(2, manufacturer.getCountry());
            createNewManufacturer.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create new manufacturer. Name: "
                    + manufacturer.getName() + " Country: " + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement GetManufacturer = connection.prepareStatement(getRequest)) {
            GetManufacturer.setLong(1, id);
            ResultSet resultSet = GetManufacturer.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = generateManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get manufacturer for id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturerList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getManufacturer.executeQuery();
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                manufacturer = generateManufacturer(resultSet);
                manufacturerList.add(manufacturer);
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all manufacturers ", e);
        }

    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update manufacturers data. Manufacturer: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteManufacturer
                         = connection.prepareStatement(deleteRequest)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete manufacturer for id: " + id, e);
        }
    }

    private Manufacturer generateManufacturer(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        Long id = resultSet.getLong("id");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setId(id);
        return manufacturer;
    }
}
