package mate.jdbc.dao;

import exception.DataProcessingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final Logger logger = LogManager.getLogger(ManufacturerDaoImpl.class);

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        logger.info("Create method was called with manufacturer={}", manufacturer);
        String insertFormatRequest = "INSERT INTO manufacturers(name, country) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(insertFormatRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            logger.debug("String of DB request={}", createManufacturersStatement.toString());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            logger.error("Create method failed for " + manufacturer, e);
            throw new DataProcessingException("Can't insert to DB new " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("Get by id method called with parameter={}", id);
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE;");
            while (resultSet.next()) {
                if (resultSet.getObject("id", Long.class).equals(id)) {
                    String name = resultSet.getString("name");
                    String country = resultSet.getString("country");
                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setId(id);
                    manufacturer.setName(name);
                    manufacturer.setCountry(country);
                    return Optional.of(manufacturer);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get manufacturer with ID=" + id, e);
            throw new DataProcessingException("Can't get manufacturer from DB by id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("Get all method called");
        List<Manufacturer> allFormats = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturersStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturersStatement
                    .executeQuery("SELECT * FROM manufacturers WHERE is_deleted = FALSE;");
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(id);
                manufacturer.setName(name);
                manufacturer.setCountry(country);
                allFormats.add(manufacturer);
            }
        } catch (SQLException e) {
            logger.error("getAll method failed", e);
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
        return allFormats;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("Update method called with parameter={}", manufacturer);
        String insertManufacturerRequest = "UPDATE taxi_service_db.manufacturers "
                + "SET name = ?, country = ? where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.setLong(3, manufacturer.getId());
            logger.debug("String of DB request={}", createManufacturersStatement.toString());
            if (createManufacturersStatement.executeUpdate() > 0) {
                logger.debug("Updated manufacturer successful");
                return manufacturer;
            } else {
                logger.info("There is no element with ID=" + manufacturer.getId());
                return null;
            }
        } catch (SQLException e) {
            logger.error("Update failed for manufacturer with ID=" + manufacturer.getId(), e);
            throw new DataProcessingException("Can't update manufacturer in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Delete method called with parameter={}", id);
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement =
                        connection.prepareStatement(deleteRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setLong(1, id);
            return createManufacturersStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            logger.error("Delete method failed for manufacturer with ID=" + id, e);
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }
}
