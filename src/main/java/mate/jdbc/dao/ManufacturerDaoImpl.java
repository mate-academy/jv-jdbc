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
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUntil.getConnection();
                  Statement getAllFormatsStatement = connection.createStatement()) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery(""
                    + "SELECT "
                    + "* FROM manufacturers"
                    + " WHERE is_delete = 0");
            while (resultSet.next()) {
                String manufacturerName = resultSet.getNString("name");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(manufacturerName);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all format DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers "
                + "SET name = ?, country = ? "
                + "WHERE is_delete = FALSE";
        try (Connection connection = ConnectionUntil.getConnection();
                PreparedStatement updateManufacturer =
                        connection.prepareStatement(updateRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
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
        String deleteRequest = "UPDATE manufacturers SET is_delete = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUntil.getConnection();
                 PreparedStatement createManufacturer =
                        connection.prepareStatement(deleteRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturer.setLong(1, id);
            return createManufacturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update manufacturer to DB", e);
        }
    }
}
