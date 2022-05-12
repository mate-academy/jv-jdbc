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
public class JdbcManufacturerDaoImpl implements JdbcManufacturerDao {
    private static final Logger logger = LogManager.getLogger(JdbcManufacturerDaoImpl.class);

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertManufacturerRequest = "INSERT INTO manufacturers (name, country) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createManufacturersStatement = connection.prepareStatement(
                           insertManufacturerRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturersStatement.setString(1, manufacturer.getName());
            createManufacturersStatement.setString(2, manufacturer.getCountry());
            createManufacturersStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturersStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert manufacturer "
                    + manufacturer + " into DB", e);
        }
        logger.info("create() method was called with manufacturer: {}" + manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("get() method was called with id: {}" + id);
        String getByIdQuery = "SELECT * FROM manufacturers  WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getByIdQuery)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get manufacturer from DB by id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("getAll() method was called");
        String getAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = false;";
        List<Manufacturer> allManufacturersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllManufacturersStatement = connection
                        .prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllManufacturersStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturersList.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all manufacturers from DB", e);
        }
        return allManufacturersList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("update() method was called with manufacturer: {}" + manufacturer);
        String updateElementRequest = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(
                        updateElementRequest)) {
            updateStatement.setString(1, manufacturer.getName());
            updateStatement.setString(2, manufacturer.getCountry());
            updateStatement.setLong(3, manufacturer.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update data in DB by id: "
                    + manufacturer.getId(), e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        logger.info("delete() method was called with id: {}" + id);
        String deleteElementRequest = "UPDATE manufacturers SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection
                        .prepareStatement(deleteElementRequest)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete data from DB by id: " + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) {
        logger.info("resultSetParse() method was called with resultSet" + resultSet);
        try {
            String name = resultSet.getString("name");
            Long id = resultSet.getObject("id", Long.class);
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can not parse data from resultSet", e);
        }
    }
}
