package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertRequest = "INSERT INTO manufacturers (name, country) value (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(insertRequest,
                                Statement.RETURN_GENERATED_KEYS);) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject("id", Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer in DB. Manufacturer: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT FROM manufacturers WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getObject("id", Long.class));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer with id = " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllRequest)) {
            getAllStatement.executeQuery();
            ResultSet generatedKeys = getAllStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(generatedKeys.getObject("id", Long.class));
                manufacturer.setName(generatedKeys.getString("name"));
                manufacturer.setCountry(generatedKeys.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get all manufacturer from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? where id = ? "
                + "AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeQuery();
            ResultSet generatedKeys = updateStatement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer with id = "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setLong(1, id);
            return createStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB", e);
        }
    }
}
