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
        String sqlRequest =
                "INSERT INTO manufacturers (name, country) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(sqlRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            pStatement.setString(1, manufacturer.getName());
            pStatement.setString(2, manufacturer.getCountry());
            pStatement.executeUpdate();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't add manufacturers to DataBase", e);
        }
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        boolean isUpdated;
        String sqlRequest = "UPDATE manufacturers"
                + " SET  name = ?, country = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            pStatement.setString(1, manufacturer.getName());
            pStatement.setString(2, manufacturer.getCountry());
            pStatement.setLong(3, manufacturer.getId());
            isUpdated = pStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't execute UPDATE for record: " + manufacturer, e);
        }
        if (!isUpdated) {
            throw new DataProcessingException("Can't UPDATE " + manufacturer);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String sqlRequest = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            pStatement.setLong(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseRecord(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get record with id=" + id + " from manufacturers", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String sqlRequest = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseRecord(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get all records from manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public boolean delete(Long id) {
        String sqlRequest = "UPDATE manufacturers"
                + " SET is_deleted = true WHERE id = ? AND is_deleted = FALSE";
        boolean isExecuted;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pStatement =
                        connection.prepareStatement(sqlRequest)) {
            pStatement.setLong(1, id);
            isExecuted = pStatement.executeUpdate() > 0;
            return isExecuted;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't execute soft DELETE for record with id:" + id, e);
        }
    }

    private Manufacturer parseRecord(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Long id = resultSet.getObject("id", Long.class);
        return new Manufacturer(id, name, country);
    }
}
