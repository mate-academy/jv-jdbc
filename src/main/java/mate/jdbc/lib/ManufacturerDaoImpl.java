package mate.jdbc.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturer(name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet resultSet = createManufacturerStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert manufacturer in DB " + e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> optionalManufacturer;
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getManufacturerStatement.executeQuery(
                    "SELECT * FROM manufacturer WHERE id = " + id + " AND is_deleted = false");
            if (resultSet.next()) {
                optionalManufacturer = Optional.of(new Manufacturer(resultSet
                        .getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getString("country")));
            } else {
                throw new RuntimeException("Data base is empty");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer from DB " + e);
        }
        return optionalManufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery(
                        "SELECT * FROM manufacturer WHERE is_deleted = false");
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer(resultSet.getObject("id", Long.class),
                        resultSet.getString("name"),
                        resultSet.getString("country"));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturer from DB ", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest =
                "UPDATE manufacturer SET id = ?, name = ?, country = ? WHERE is_deleted = false";
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement updateManufacturerStatement =
                     connection.prepareStatement(updateRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturer SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer from DB " + e);
        }
    }
}
