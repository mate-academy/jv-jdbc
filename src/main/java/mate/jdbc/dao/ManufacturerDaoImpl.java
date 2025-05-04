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
    private static final int INDEX_ID = 1;
    private static final int INDEX_NAME = 2;
    private static final int INDEX_COUNTRY = 3;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement = connection
                        .prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(INDEX_ID, manufacturer.getName());
            createManufacturerStatement.setString(INDEX_NAME, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_ID, Long.class);
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
        String getManufacturerRequest = "SELECT * FROM manufacturers where id = ? AND "
                + "is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturerRequest)) {
            getManufacturerStatement.setLong(INDEX_ID, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturerFromResultSet(resultSet);
            }
            return Optional.ofNullable(manufacturer);
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
                        .prepareStatement(getAllManufacturerRequest)) {
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
                        .prepareStatement(updateManufacturerRequest)) {
            updateManufacturerStatement.executeUpdate();
            return manufacturer;
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
                        .prepareStatement(deleteManufacturerRequest)) {
            deleteManufacturerStatement.setLong(INDEX_ID, id);
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
