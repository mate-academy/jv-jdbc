package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.DataProcessingException;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest
                = "INSERT INTO manufacturers (name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
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
            throw new DataProcessingException("Can't insert manufacturer to DB", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getByIdManufacturerRequest
                = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        Manufacturer manufacturer = new Manufacturer();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getByIdManufacturerRequest)) {
            getManufacturerStatement.setLong(1, id);
            manufacturer = getResultSet(getManufacturerStatement).get(0);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from DB", e);
        }
        return Optional.of(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturerRequest = "SELECT * FROM manufacturers;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturerStatement =
                        connection.prepareStatement(getAllManufacturerRequest)) {
            return getResultSet(getAllManufacturerStatement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET country = ? WHERE name = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement =
                        connection.prepareStatement(updateManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            updateStatement.setString(1, manufacturer.getCountry());
            updateStatement.setString(2, manufacturer.getName());
            ResultSet generatedKeys = updateStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data to DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deletedManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement =
                        connection.prepareStatement(deletedManufacturerRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data from DB", e);
        }
    }

    private List<Manufacturer> getResultSet(PreparedStatement statement) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String county = resultSet.getString("country");
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(county);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from " + statement, e);
        }
        return manufacturers;
    }
}
