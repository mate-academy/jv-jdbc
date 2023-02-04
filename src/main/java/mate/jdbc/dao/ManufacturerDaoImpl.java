package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.ConnectionUntil;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT "
                + "INTO manufacturers(name, country) "
                + "VALUES(?, ?)";
        try (Connection connection = ConnectionUntil.getConnection();
                PreparedStatement createManufacturer =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setString(1, manufacturer.getName());
            createManufacturer.setString(2, manufacturer.getCountry());
            createManufacturer.executeUpdate();
            ResultSet generatedKeys = createManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = " SELECT "
                + "* FROM manufacturers"
                + " WHERE is_deleted = FALSE"
                + "&& id = ?";
        try (Connection connection = ConnectionUntil.getConnection();
                 PreparedStatement getStatement =
                        connection.prepareStatement(getRequest)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            return Optional.ofNullable(parseManufacturer(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get data from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getRequest = " SELECT "
                + "* FROM manufacturers"
                + " WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUntil.getConnection();
                  PreparedStatement getAllManufacturersStatement =
                         connection.prepareStatement(getRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = parseManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get data from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUntil.getConnection();
                PreparedStatement updateManufacturer =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update manufacturer to DB", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers "
                + "SET is_deleted = TRUE "
                + "WHERE id = ?";
        try (Connection connection = ConnectionUntil.getConnection();
                 PreparedStatement createManufacturer =
                        connection.prepareStatement(deleteRequest)) {
            createManufacturer.setLong(1, id);
            return createManufacturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update manufacturer to DB", e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getNString("name"));
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get data from DB", e);
        }
        return manufacturer;
    }
}
