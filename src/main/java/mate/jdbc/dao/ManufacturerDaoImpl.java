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
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest
                = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createManufacturerStatements
                         = connection.prepareStatement(insertManufacturerRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatements.setString(1, manufacturer.getName());
            createManufacturerStatements.setString(2, manufacturer.getCountry());
            createManufacturerStatements.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatements.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB.", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getManufacturerRequest
                = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturersStatements
                         = connection.prepareStatement(getManufacturerRequest)) {
            getManufacturersStatements.setLong(1, id);
            ResultSet resultSet = getManufacturersStatements.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer(resultSet.getString("name"),
                        resultSet.getString("country"));
                manufacturer.setId(resultSet.getObject("id", Long.class));
                return Optional.of(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB. Id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllManufacturerRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        List<Manufacturer> output = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllManufacturersStatements = connection.createStatement()) {
            ResultSet resultSet
                    = getAllManufacturersStatements.executeQuery(getAllManufacturerRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Long id = resultSet.getObject("id", Long.class);
                Manufacturer manufacturer = new Manufacturer(name, country);
                manufacturer.setId(id);
                output.add(manufacturer);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturers from DB", e);
        }
        return output;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateManufacturerRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturersStatements
                          = connection.prepareStatement(updateManufacturerRequest)) {
            updateManufacturersStatements.setString(1, manufacturer.getName());
            updateManufacturersStatements.setString(2, manufacturer.getCountry());
            updateManufacturersStatements.setLong(3, manufacturer.getId());
            updateManufacturersStatements.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturers in DB", e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteManufacturerRequest = "UPDATE manufacturers"
                + " SET is_deleted = true WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturersStatements
                         = connection.prepareStatement(deleteManufacturerRequest)) {
            updateManufacturersStatements.setLong(1, id);
            return updateManufacturersStatements.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB. Id: " + id, e);
        }
    }
}
