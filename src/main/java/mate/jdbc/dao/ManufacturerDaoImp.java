package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImp implements ManufacturerDao {
    private static final String insertManufacturerQuery =
            "INSERT INTO manufacturers (name, country) VALUES(?, ?)";
    private static final String getALLManufacturersQuery =
            "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
    private static final String deleteManufacturerQuery =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
    private static final String getManufacturerQuery =
            "SELECT name, country, id FROM manufacturers WHERE id = ?";
    private static final String updateManufacturerQuery =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNewManufacturer = connection.prepareStatement(
                        insertManufacturerQuery, Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturer.setString(1, manufacturer.getName());
            createNewManufacturer.setString(2, manufacturer.getCountry());
            createNewManufacturer.executeUpdate();
            ResultSet generatedKeys = createNewManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant insert manufacturer with id " + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection.prepareStatement(
                        getManufacturerQuery)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(createManufacturer(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant get manufacturer from DB with id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturers =
                        connection.prepareStatement(getALLManufacturersQuery)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = createManufacturer(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturer = connection.prepareStatement(
                        updateManufacturerQuery)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant update manufacturer with id " + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturer = connection.prepareStatement(
                        deleteManufacturerQuery, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant delete manufacturer from DB with id " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getObject("id", Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
