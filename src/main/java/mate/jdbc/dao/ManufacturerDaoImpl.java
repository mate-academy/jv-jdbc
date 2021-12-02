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
import mate.jdbc.lib.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturersRequest = "INSERT INTO manufacturers(name, country)"
                + " values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertPreparedStatement
                        = connection.prepareStatement(insertManufacturersRequest,
                        Statement.RETURN_GENERATED_KEYS)) {

            insertPreparedStatement.setString(1, manufacturer.getName());
            insertPreparedStatement.setString(2, manufacturer.getCountry());
            insertPreparedStatement.executeUpdate();
            ResultSet generatedKeys = insertPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturers "
                    + manufacturer + " to DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String selectManufacturerRequest
                = "SELECT * FROM manufacturers WHERE is_deleted < 1 AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getPreparedStatement
                        = connection.prepareStatement(selectManufacturerRequest)) {
            getPreparedStatement.setLong(1, id);
            ResultSet resultSet = getPreparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createManufacturer(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id from DB."
                    + " Manufacturer id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted < 1;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(getAllManufacturerRequest)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(createManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest
                = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE is_deleted < 1 AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                        = connection.prepareStatement(updateManufacturerRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer data in DB. "
                    + "Manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest
                = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement
                        = connection.prepareStatement(deleteManufacturerRequest)) {
            updateStatement.setLong(1, id);
            return updateStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id from DB. "
                    + "Manufacturer id: " + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }
}
