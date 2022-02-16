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
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB, manufacturer: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getManufacturerRequest = "SELECT * FROM manufacturers where id = ? AND "
                + "is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturerFromResultSet(resultSet);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB by id: "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers where is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement = connection
                        .prepareStatement(getAllManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = getManufacturerFromResultSet(resultSet);
                allManufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = '"
                + manufacturer.getName() + "', country = '"
                + manufacturer.getCountry() + "' where id = "
                + manufacturer.getId();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            return updateManufacturerStatement.executeUpdate() >= 1 ? manufacturer : null;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB by id: "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers "
                + "SET is_deleted = true where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB by id: " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject("id", Long.class);
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        return manufacturer;
    }
}
