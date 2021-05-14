package mate.jdbc.dao;

import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao{
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers (name,country) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement createStatement = connection.prepareStatement(insertRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer: "
                    + manufacturer.toString(), e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest = "SELECT * FROM manufacturers WHERE id = ? "
                + "AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatement =
                     connection.prepareStatement(selectManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturer = new Manufacturer(id, name, country);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery("SELECT * FROM manufacturers WHERE"
                    + " is_deleted = false");
            List<Manufacturer> list = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
                list.add(manufacturer);
            }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement = connection
                     .prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                return manufacturer;
            }
            throw new RuntimeException("Manufacturer with id = " + manufacturer.getId()
                    + "not found in db");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by id:"
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = ture WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createStatement = connection.prepareStatement(deleteRequest)) {
            createStatement.setLong(1, id);
            return createStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id: " + id, e);
        }
    }
}
