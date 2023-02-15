package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.error.DataProcessingException;
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
        logger.info("Create Manufacturer method was called. name={}, country={}",
                manufacturer.getName(), manufacturer.getCountry());
        String createManufacturerRequest =
                "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(createManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {

            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            logger.info("Manufacturer was created: manufacturer={}", manufacturer);
            return manufacturer;
        } catch (SQLException e) {
            logger.error("Can not create manufacturer in DB.", e);
            throw new DataProcessingException("Can not create manufacturer in DB.", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("Get Manufacturer method was called. id={}", id);
        String getManufacturerRequest =
                "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement =
                        connection.prepareStatement(getManufacturerRequest)) {

            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                Manufacturer manufacturer = getManufacturer(resultSet);
                logger.info("Manufacturer was received: manufacturer={}", manufacturer);
                return Optional.of(manufacturer);
            }
            logger.info("Received Manufacturer was empty");
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Received Manufacturer was failed", e);
            throw new DataProcessingException("Can not get manufacturer from DB by id.", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("getAll Manufacturers method was called.");
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            List<Manufacturer> manufacturerList = new ArrayList<>();
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = 0;");

            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            logger.error("Receiving list of Manufacturers was failed", e);
            throw new DataProcessingException("Can not receive list of manufacturers.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("update Manufacturer method was called. manufacturer={}", manufacturer);
        String updateManufacturerRequest =
                "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateManufacturerRequest)) {

            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            logger.info("Manufacturer was updated");
            return manufacturer;
        } catch (SQLException e) {
            logger.error("Update Manufacturers was failed", e);
            throw new DataProcessingException("Can not update manufacturer in DB.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("delete Manufacturer method was called. id={}", id);
        String createManufacturerRequest =
                "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement softDeleteManufacturerStatement =
                        connection.prepareStatement(createManufacturerRequest)) {

            softDeleteManufacturerStatement.setLong(1, id);
            int counter = softDeleteManufacturerStatement.executeUpdate();
            logger.info("Delete Manufacturer was completed with success");
            return counter > 0;
        } catch (SQLException e) {
            logger.error("Delete Manufacturer was failed", e);
            throw new DataProcessingException("Can not soft delete manufacturer from DB.", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
