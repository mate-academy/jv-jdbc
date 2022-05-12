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
        logger.info("create() method started with name -> {}, country -> {}",
                manufacturer.getName(), manufacturer.getCountry());
        String insertManufacturerRequest = "INSERT INTO manufacturers(name, country) values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturerStatement =
                        connection.prepareStatement(insertManufacturerRequest,
                                Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1,manufacturer.getName());
            createManufacturerStatement.setString(2,manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer in DB: "
                    + manufacturer, e);
        }
        logger.info("create() method completed successfully with name -> {}, country -> {}",
                manufacturer.getName(), manufacturer.getCountry());
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("get() method started with id -> {}", id);
        String getManufacturer = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getManufacturer)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if (resultSet.next()) {
                Optional<Manufacturer> optional =
                        Optional.of(parseManufacturerFromResultSet(resultSet));
                logger.info("get() method completed successfully with id -> {}", id);
                return optional;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer from DB with id -> " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("getAll() method started");
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllRequest = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getAllManufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = getAllManufacturerStatement
                    .executeQuery(getAllRequest);
            while (resultSet.next()) {
                manufacturerList.add(parseManufacturerFromResultSet(resultSet));
            }
            logger.info("getAll() method completed successfully");
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturer from DB",e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("update() method started with id -> {}", manufacturer.getId());
        String updateRequest = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateManufacturerStatement =
                        connection.prepareStatement(updateRequest)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
            logger.info("update() method completed successfully with id -> {}",
                    manufacturer.getId());
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer to DB with id -> "
                    + manufacturer.getId()
                    + " name -> " + manufacturer.getName()
                    + " country -> " + manufacturer.getCountry(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("delete() method started with id -> {}", id);
        String deleteRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteManufacturerStatement =
                        connection.prepareStatement(deleteRequest)) {
            deleteManufacturerStatement.setLong(1, id);
            int quantity = deleteManufacturerStatement.executeUpdate();
            logger.info("delete() method completed successfully with id -> {}", id);
            return quantity >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer to DB with id -> "
                    + id, e);
        }
    }

    private Manufacturer parseManufacturerFromResultSet(ResultSet resultSet) {
        try {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getObject("id", Long.class));
            manufacturer.setName(resultSet.getString("name"));
            manufacturer.setCountry(resultSet.getString("country"));
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't parse manufacturer from resultSet ", e);
        }
    }

//    private List<Manufacturer> parser(ResultSet resultSet) {
//
//        try {
//            while (resultSet.next()) {
//                Long id = resultSet.getObject("id", Long.class);
//                String name = resultSet.getString("name");
//                String country = resultSet.getString("country");
//                Manufacturer manufacturer = new Manufacturer();
//                manufacturer.setId(id);
//                manufacturer.setName(name);
//                manufacturer.setCountry(country);
//                manufacturerList.add(manufacturer);
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException("Can't parse resultSet to List");
//        }
//        return manufacturerList;
//    }
}
