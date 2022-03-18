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
import mate.jdbc.util.ConnectionsUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String insertSqlQuery = "INSERT INTO manufacturer (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement insertManufacturerStatement = connection
                        .prepareStatement(insertSqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertManufacturerStatement.setString(1, manufacturer.getName());
            insertManufacturerStatement.setString(2, manufacturer.getCountry());
            insertManufacturerStatement.executeUpdate();
            ResultSet resultSet = insertManufacturerStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer to DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Optional<Manufacturer> result = Optional.empty();
        String getSqlQuery = "SELECT * FROM manufacturer WHERE is_deleted = FALSE AND id = ?";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement getManufacturerStatement = connection
                        .prepareStatement(getSqlQuery)) {
            getManufacturerStatement.setLong(1, id);
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                result = Optional.ofNullable(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer form DB, id: " + id, e);
        }
        return result;
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        String getAllSqlQuery = "SELECT * FROM manufacturer WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionsUtil.getConnection();
                 PreparedStatement getAllManufacturerStatement = connection
                         .prepareStatement(getAllSqlQuery)) {
            ResultSet resultSet = getAllManufacturerStatement.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers form DB", e);
        }
        return allManufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateSqlQuery = "UPDATE manufacturer SET name = ?, country = ? WHERE id = ?";
        try (Connection connection = ConnectionsUtil.getConnection();
                 PreparedStatement updateManufacturerStatement = connection
                        .prepareStatement(updateSqlQuery)) {
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in DB: "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        String deleteSqlQuery = "UPDATE manufacturer SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionsUtil.getConnection();
                PreparedStatement deleteManufacturerStatement = connection
                        .prepareStatement(deleteSqlQuery)) {
            deleteManufacturerStatement.setLong(1, id);
            return deleteManufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer form DB, id: " + id, e);
        }
    }

    private Manufacturer parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        return new Manufacturer(id, name, country);
    }
}
