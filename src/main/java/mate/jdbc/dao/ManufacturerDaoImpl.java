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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final Logger logger = LogManager.getLogger(ManufacturerDaoImpl.class);

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String saveRequest = "INSERT INTO manufacturers(name, country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement saveStatement =
                        connection.prepareStatement(saveRequest, Statement.RETURN_GENERATED_KEYS)) {
            saveStatement.setString(1, manufacturer.getName());
            saveStatement.setString(2, manufacturer.getCountry());
            saveStatement.executeUpdate();
            ResultSet resultSet = saveStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
            logger.info("Create method finished successfully. Manufacturer's name: {}, country: {}",
                    manufacturer.getName(), manufacturer.getCountry());
        } catch (SQLException e) {
            throw new DataProcessingException("Error happened when saving manufacturer with name "
                    + manufacturer.getName() + " and country " + manufacturer.getCountry(), e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String getRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(getRequest)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                logger.info("Manufacturer with id {} was found in DB", id);
                return Optional.of(parseResultSet(resultSet));
            }
            logger.info("Manufacturer with id {} wasn't found in DB returned empty optional", id);
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error happened when getting manufacturer by id "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                manufacturers.add(parseResultSet(resultSet));
            }
            logger.info("Returned all manufacturers from DB successfully");
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Error happened when getting all "
                    + "manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            if (updateStatement.executeUpdate() > 0) {
                logger.info("Manufacturer with id {} was successfully updated",
                        manufacturer.getId());
                return manufacturer;
            }
            throw new DataProcessingException("Manufacturer with id - " + manufacturer.getId()
                    + " wasn't found in DB");
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred when updating manufacturer "
                    + "with id - " + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteRequest)) {
            deleteStatement.setLong(1, id);
            int updatedAmount = deleteStatement.executeUpdate();
            logger.info("Manufacturer with id {} was {}",
                    id, updatedAmount > 0 ? "successfully deleted from DB." : "not present in DB.");
            return updatedAmount > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error happened when deleting manufacturer with id - "
                    + id, e);
        }
    }

    @Override
    public void clearTable() {
        String deleteALlRequest = "TRUNCATE manufacturers";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement clearTableStatement = connection.createStatement()) {
            clearTableStatement.executeUpdate(deleteALlRequest);
            logger.info("Table manufacturers was successfully cleaned");
        } catch (SQLException e) {
            throw new DataProcessingException("Error happened when cleaning the table. "
                    + "Table name: manufacturers", e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setId(resultSet.getObject("id", Long.class));
        return manufacturer;
    }
}
