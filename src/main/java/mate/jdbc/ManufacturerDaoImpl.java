package mate.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        String name = manufacturer.getName();
        String country = manufacturer.getCountry();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, name);
            createManufacturer.setString(2, country);
            createManufacturer.executeUpdate();
            ResultSet generatedKeys = createManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                return new Manufacturer(id, name, country);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't create manufacturer by name: " + name, throwables);
        }
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getQuery = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer =
                        connection.prepareStatement(getQuery)) {
            getManufacturer.setObject(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(new Manufacturer(id, name, country));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get data by id: " + id, throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllManufacturers.executeQuery(getAllQuery);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                manufacturers.add(manufacturer);
            }
            return manufacturers;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get data from DB", throwables);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        Long id = manufacturer.getId();
        String name = manufacturer.getName();
        String country = manufacturer.getCountry();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, name);
            updateManufacturer.setString(2, country);
            updateManufacturer.setObject(3, id);
            updateManufacturer.executeUpdate();
            return new Manufacturer(id, name, country);
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't update data by id: " + id, throwables);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturer =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturer.setObject(1, id);
            int deletedRows = deleteManufacturer.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete data by id: " + id, throwables);
        }
    }
}
