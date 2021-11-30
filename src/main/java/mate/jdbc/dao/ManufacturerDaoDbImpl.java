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
import mate.jdbc.lib.exceptions.DataProcessingException;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoDbImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerStatement =
                "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerStatement,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer "
                    + manufacturer + " in DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getManufactureById = "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getManufacturer =
                        connection.prepareStatement(getManufactureById)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            while (resultSet.next()) {
                Long manufacturerId = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer.setId(manufacturerId);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer "
                    + manufacturer + " from DB", e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufactures =
                "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getAllManufacturers =
                        connection.prepareStatement(getAllManufactures)) {
            ResultSet resultSet = getAllManufacturers.executeQuery(getAllManufactures);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerStatement = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(updateManufacturerStatement)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.setLong(3, manufacturer.getId());
            createManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer "
                    + manufacturer + " in DB by", e);
        }
        return get(manufacturer.getId()).get();
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturerStatement.setLong(1, id);
            return createManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't to do soft delete "
                    + "of the manufacturer in DB by id " + id, e);
        }
    }
}
