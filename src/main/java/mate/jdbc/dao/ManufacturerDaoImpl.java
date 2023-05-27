package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exceptions.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Manufacturer> getAll() {
        String sqlRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.createConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                manufacturers.add(new Manufacturer().setId(id).setName(name).setCountry(country));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve records from DB", e);
        }
    }

    public Optional<Manufacturer> get(Long id) {
        String sqlRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.createConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                return Optional.of(new Manufacturer().setId(id).setName(name).setCountry(country));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve record with id "
                    + id + " form DB", e);
        }
        logger.info("There is no record for id {} in the database", id);
        return Optional.empty();
    }

    public Manufacturer create(Manufacturer manufacturer) {
        String sqlRequest = "INSERT INTO manufacturers(name, country) values(?,?)";
        try (Connection connection = ConnectionUtil.createConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                manufacturer.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new record for " + manufacturer, e);
        }
        return manufacturer;
    }

    public Manufacturer update(Manufacturer manufacturer) {
        String sqlRequest = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.createConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setObject(3, manufacturer.getId());
            if (statement.executeUpdate() != 1) {
                logger.info("There is no record for {} in the database", manufacturer);
                return null;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update record for " + manufacturer, e);
        }
        return manufacturer;
    }

    public boolean delete(Long id) {
        String sqlRequest = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.createConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setObject(1, id);
            if (statement.executeUpdate() != 1) {
                logger.info("There is no record with id {} in the database", id);
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete record for id " + id, e);
        }
        return true;
    }
}

