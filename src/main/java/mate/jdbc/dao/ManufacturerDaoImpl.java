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
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                getManufacturedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getManufacturedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers "
                + "(`name`, `country`) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                createManufacturedStatement = connection.prepareStatement(insertManufacturerRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturedStatement.setString(1, manufacturer.getName());
            createManufacturedStatement.setString(2, manufacturer.getCountry());
            createManufacturedStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturedStatement.getGeneratedKeys();
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
                "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                getManufacturedStatement = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturedStatement.setLong(1, id);
            ResultSet resultSet = getManufacturedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                return Optional.of(manufacturer);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? "
                + "AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                updateManufacturedStatement = connection.prepareStatement(updateRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturedStatement.setString(1, manufacturer.getName());
            updateManufacturedStatement.setString(2, manufacturer.getCountry());
            updateManufacturedStatement.setString(3, String.valueOf(manufacturer.getId()));
            updateManufacturedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update manufacturer "
                    + manufacturer + " from DB", e);
        }

        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequst = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement
                createManufacturedStatement = connection.prepareStatement(deleteRequst,
                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturedStatement.setLong(1, id);
            return createManufacturedStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
    }
}
