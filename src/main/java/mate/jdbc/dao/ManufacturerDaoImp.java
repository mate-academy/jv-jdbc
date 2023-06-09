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
    private static final String insertManufacturerRequest =
            "INSERT INTO manufacturers (name, country) values(?, ?)";
    private static final String getALLManufacturersRequest =
            "SELECT * FROM manufacturers WHERE is_deleted = false";
    private static final String deleteManufacturerRequest =
            "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
    private static final String getManufacturerRequest =
            "SELECT name, country FROM manufacturers WHERE id = ?";
    private static final String updateManufacturerRequest =
            "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?";

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createNewManufacturer = connection.prepareStatement(
                        insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createNewManufacturer.setString(1, manufacturer.getName());
            createNewManufacturer.setString(2, manufacturer.getCountry());
            createNewManufacturer.executeUpdate();
            ResultSet generatedKeys = createNewManufacturer.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant insert manufacturer with name " + manufacturer.getName(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturer = connection.prepareStatement(
                        getManufacturerRequest)) {
            getManufacturer.setLong(1, id);
            ResultSet resultSet = getManufacturer.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = createManufacturer(resultSet);
                return Optional.ofNullable(manufacturer);
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
                        connection.prepareStatement(getALLManufacturersRequest)) {
            ResultSet resultSet = getAllManufacturers.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(id, name, country);
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
                        updateManufacturerRequest)) {
            updateManufacturer.setString(1, manufacturer.getName());
            updateManufacturer.setString(2, manufacturer.getCountry());
            updateManufacturer.setLong(3, manufacturer.getId());
            updateManufacturer.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant update manufacturer with name " + manufacturer.getName(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturer = connection.prepareStatement(
                        deleteManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturer.setLong(1, id);
            return deleteManufacturer.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cant delete manufacturer from DB with id " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        return new Manufacturer(resultSet.getObject(1, Long.class),
                resultSet.getString("name"),
                resultSet.getString("country"));
    }
}
