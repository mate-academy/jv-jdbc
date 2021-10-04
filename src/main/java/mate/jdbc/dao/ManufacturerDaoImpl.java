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
import mate.jdbc.lib.ConnectionUtil;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturer = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(insertManufacturer,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, manufacturer.getName());
            createFormatStatement.setString(2, manufacturer.getCountry());
            createFormatStatement.executeUpdate();
            ResultSet generatedKey = createFormatStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getById = "SELECT * FROM manufacturers WHERE id = '" + id + "';";
        Optional<Manufacturer> manufacturerOptional = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFormatsStatement = connection.prepareStatement(getById)) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturerOptional = Optional.ofNullable(getManufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get by index manufacturer from DB in this id: "
                    + id, e);
        }
        return manufacturerOptional;
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturer = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFormatsStatement =
                        connection.prepareStatement(getAllManufacturer)) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id",Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturersList.add(getManufacturer(id, name, country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return manufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?  WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateData =
                        connection.prepareStatement(updateRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            updateData.setLong(3, manufacturer.getId());
            updateData.setString(1, manufacturer.getName());
            updateData.setString(2, manufacturer.getCountry());
            updateData.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer"
                    + manufacturer + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteById =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            deleteById.setLong(1, id);
            return deleteById.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB in this id: "
                    + id + ";", e);
        }
    }
    private Manufacturer getManufacturer(Long id, String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
