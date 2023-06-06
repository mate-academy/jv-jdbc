package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country)"
                + " VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement
                        = connection.prepareStatement(insertManufacturerRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, manufacturer.getName());
            createStatement.setString(2, manufacturer.getCountry());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer "
                    + manufacturer + " to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturer =
                "SELECT * FROM manufacturers WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement =
                        connection.prepareStatement(getManufacturer)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getManufacturersInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id: "
                    + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet =
                        getAllStatement.executeQuery(getAllManufacturerRequest);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturersInstance(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ?"
                        + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(
                        updateManufacturerRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer: "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers SET is_deleted = TRUE"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(
                        deleteManufacturerRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id "
                    + id, e);
        }
    }

    private Manufacturer getManufacturersInstance(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            return new Manufacturer(id, name, country);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't process manufacturer data with result set: "
                    + resultSet, e);
        }
    }
}
