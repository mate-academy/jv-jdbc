package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest = "INSERT INTO manufacturer(name, country) value(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();

                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(createManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generateKeys = createManufacturerStatement.getGeneratedKeys();
            if (generateKeys.next()) {
                Long id = generateKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest = "SELECT * FROM manufacturer WHERE is_deleted = false "
                + "AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return Optional.ofNullable(manufacturer);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturer WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement
                        = connection.prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
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
        String updateManufactureRequest = "UPDATE manufacturer SET `name` = ?, `country` = ? "
                + "WHERE (`id` = ?) AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateFormatStatement
                        = connection.prepareStatement(updateManufactureRequest)) {
            updateFormatStatement.setLong(3, manufacturer.getId());
            updateFormatStatement.setString(1, manufacturer.getName());
            updateFormatStatement.setString(2, manufacturer.getCountry());
            updateFormatStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer id "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufactureRequest = "UPDATE manufacturer SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufactureStatement
                        = connection.prepareStatement(deleteManufactureRequest)) {
            deleteManufactureStatement.setLong(1, id);
            return deleteManufactureStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer id " + id, e);
        }
    }
}
