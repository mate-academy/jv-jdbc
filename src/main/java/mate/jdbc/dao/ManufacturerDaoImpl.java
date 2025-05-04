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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name,country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement
                        = connection.prepareStatement(
                                createManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet resultSet = createManufacturerStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> resultManufacturer = Optional.empty();
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id=? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerByIdStatement
                        = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturerByIdStatement.setLong(1, id);
            ResultSet resultSet =
                    getManufacturerByIdStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = parseResultSet(resultSet);
                resultManufacturer = Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer by id", e);
        }
        return resultManufacturer;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturers = "SELECT * FROM manufacturers "
                + "WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement
                        = connection.prepareStatement(getAllManufacturers)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = parseResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?"
                        + ", country = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement
                        = connection.prepareStatement(
                            updateManufacturerRequest,Statement.RETURN_GENERATED_KEYS)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            ResultSet resultSet =
                    updateManufacturerStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = TRUE"
                        + " WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement
                         = connection.prepareStatement(
                           deleteManufacturerRequest,Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer by id", e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Long id = resultSet.getLong("id");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't parse resultSet", e);
        }
    }
}
